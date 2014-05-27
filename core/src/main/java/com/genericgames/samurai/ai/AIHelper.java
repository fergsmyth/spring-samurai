package com.genericgames.samurai.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.ai.patrolpattern.PatrolPattern;
import com.genericgames.samurai.ai.patrolpattern.PatrolStep;
import com.genericgames.samurai.ai.performers.AIActionPerformer;
import com.genericgames.samurai.ai.performers.AIActionPerformerProvider;
import com.genericgames.samurai.ai.routefinding.*;
import com.genericgames.samurai.maths.MyMathUtils;
import com.genericgames.samurai.model.PlayerCharacter;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.character.WorldCharacter;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.movable.character.ai.AI;
import com.genericgames.samurai.model.movable.character.ai.ActionState;
import com.genericgames.samurai.model.movable.character.ai.Enemy;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.script.GroovyManager;
import com.genericgames.samurai.script.Script;
import com.genericgames.samurai.utility.CoordinateSystem;
import com.genericgames.samurai.utility.MovementVector;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class AIHelper {

    private static final Random RANDOM = new Random();
    private static final float HEALTH_REGEN_SPEED = 0.1f;

    /**
     * Checks if a player is within an enemy's field of vision.
     * If he is, then check if there's a clear line of sight between the two.
     */
    public static void detectAIAwareness(SamuraiWorld samuraiWorld){
        World physicalWorld = samuraiWorld.getPhysicalWorld();
        for(Contact contact : physicalWorld.getContactList()){
            if(contact.isTouching()){
                if(PhysicalWorldHelper.isBetweenPlayerAndEnemyAwarenessField(contact)){
                    Enemy enemy = PhysicalWorldHelper.getEnemy(contact);
                    if(PhysicalWorldHelper.clearLineBetween(samuraiWorld.getPlayerCharacter(), enemy, physicalWorld)){
                        enemy.setPlayerAware(true);
                    }
                }

                callForSupport(contact);
            }
        }
    }
    /**
     * Checks if a enemy is within an player's combat zone.
     * If he is, then check if there's a clear path the two.
     */
    public static boolean enemyIsInCombatRange(SamuraiWorld samuraiWorld, Enemy enemy){
        World physicalWorld = samuraiWorld.getPhysicalWorld();
        for(Contact contact : physicalWorld.getContactList()){
            if(contact.isTouching()){
                if(PhysicalWorldHelper.isBetweenEnemyAndPlayerCombatZone(contact)){
                    if(PhysicalWorldHelper.clearLineBetween(samuraiWorld.getPlayerCharacter(), enemy, physicalWorld)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * If either body in this contact is a "playerAware enemy", call for support
     * (i.e. set both playerAware to true).
     * @param contact
     */
    private static void callForSupport(Contact contact) {
        if(PhysicalWorldHelper.isBetweenSupportCallFields(contact)){
            Enemy enemyA = (Enemy)contact.getFixtureA().getBody().getUserData();
            Enemy enemyB = (Enemy)contact.getFixtureB().getBody().getUserData();

            boolean eitherIsPlayerAware = enemyA.isPlayerAware() || enemyB.isPlayerAware();
            enemyA.setPlayerAware(eitherIsPlayerAware);
            enemyB.setPlayerAware(eitherIsPlayerAware);
        }
    }

    /**
     * handles all movement & combat for AI.
     */
    public static void handleAIActions(SamuraiWorld samuraiWorld) {
        for(Enemy enemy : samuraiWorld.getEnemies()){
            if(!enemy.getState().isDead()){
                if (enemy.isPlayerAware()){
                    boolean incomingArrow = arrowIsIncoming(samuraiWorld, enemy);
                    //Not proud of this :-(
                    if(enemyIsInCombatRange(samuraiWorld, enemy) || enemyIsPerformingAIAction(enemy)
                            || !samuraiWorld.getPlayerCharacter().isAlive()
                            || incomingArrow){
                        performCombatAction(samuraiWorld, enemy, incomingArrow);
                    }
                    else {
                        performRouteFindingToCharacter(samuraiWorld, enemy, samuraiWorld.getPlayerCharacter());
                    }
                }
                else {
                    performPatrolPattern(samuraiWorld, enemy);
                }
            }
        }
    }

    private static void performPatrolPattern(SamuraiWorld samuraiWorld, Enemy enemy) {
        PatrolPattern patrolPattern = enemy.getPatrolPattern();
        PatrolStep currentStep = patrolPattern.getCurrentStep();
        if(currentStep == null){
            currentStep = patrolPattern.getPatrolSteps().get(0);
        }
        if(currentStep.isStepComplete(samuraiWorld, enemy)){
            //Get next patrol step:
            int indexOfNewStep = (patrolPattern.getPatrolSteps().indexOf(currentStep)+1)
                    % patrolPattern.getPatrolSteps().size();
            currentStep = patrolPattern.getPatrolSteps().get(indexOfNewStep);
        }
        patrolPattern.setCurrentStep(currentStep);
        currentStep.processStep(samuraiWorld, enemy);
    }

    private static void performRouteFindingToCharacter(SamuraiWorld samuraiWorld, AI ai, WorldCharacter targetCharacter) {
        World physicalWorld = samuraiWorld.getPhysicalWorld();
        Collection<Fixture> bodyFixturesToIgnore = new HashSet<Fixture>();
        bodyFixturesToIgnore.add(PhysicalWorldHelper.getBodyFixtureFor(targetCharacter, physicalWorld));

        performRouteFindingToPoint(samuraiWorld, ai, targetCharacter.getX(), targetCharacter.getY(),
                bodyFixturesToIgnore);
    }

    public static void performRouteFindingToPoint(SamuraiWorld samuraiWorld, AI ai, float targetX, float targetY,
                                                   Collection<Fixture> bodyFixturesToIgnore) {
        World physicalWorld = samuraiWorld.getPhysicalWorld();
        Vector2 directionVector;
        if(PhysicalWorldHelper.clearPathBetween(ai, targetX, targetY, bodyFixturesToIgnore,
                physicalWorld)){
            ai.getRoute().setStale(true);
            //Look in player's direction:
            directionVector = MyMathUtils.getVectorFromTwoPoints(
                    ai.getX(), ai.getY(), targetX, targetY);
        }
        else {
            if(ai.getRoute()==null || ai.getRoute().getMapNodes().isEmpty() || ai.getRoute().isStale()){
                RouteCostMap upToDateRouteCostMap = RouteFindingHelper.getUpToDateRouteCostMap(samuraiWorld.getCurrentLevel());
                AStar aStar = new AStar(ai.getX(), ai.getY(),
                        targetX, targetY,
                        upToDateRouteCostMap);
                ai.setRoute(new Route(aStar.findPath()));
            }
            RouteFindingHelper.getNextRouteNode(ai, physicalWorld);
            MapNode mapNode = ai.getRoute().getCurrentTargetNode();

            float targetNodeX = mapNode.getPositionX() + 0.5f;
            float targetNodeY = mapNode.getPositionY() + 0.5f;
            directionVector = MyMathUtils.getVectorFromTwoPoints(
                    ai.getX(), ai.getY(), targetNodeX, targetNodeY);
        }

        MovementVector movementVector = new MovementVector(directionVector);
        movementVector.forwardMovement(ai.getSpeed());

        if(movementVector.hasMoved()){
            ai.setState(State.WALKING);
            ai.incrementStateTime();
        }
        else {
            ai.setState(State.IDLE);
        }

        PhysicalWorldHelper.moveBody(samuraiWorld.getPhysicalWorld(), ai, directionVector,
                movementVector.getScaledMovementVector(ai.getSpeed()));
    }

    private static void performRandomCombatAction(SamuraiWorld samuraiWorld, Enemy enemy) {
        PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
        //Look in player's direction:
        Vector2 directionVector = MyMathUtils.getVectorFromTwoPoints(
                enemy.getX(), enemy.getY(), playerCharacter.getX(), playerCharacter.getY());
        enemy.setRotation(CoordinateSystem.getRotationAngleInRadians(directionVector));

        AIActionPerformer aiActionPerformer = enemy.getAIActionPerformer();
        if(!enemyIsPerformingAIAction(enemy)){
            ActionState randomActionState = Arrays.asList(ActionState.values())
                    .get(RANDOM.nextInt(ActionState.getVoluntaryActionStates().size()));
            aiActionPerformer = AIActionPerformerProvider.getActionPerformer(randomActionState, enemy);
        }
        aiActionPerformer.performAction(samuraiWorld);
    }

    private static void performCombatAction(SamuraiWorld samuraiWorld, Enemy enemy, boolean incomingArrow) {
        PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
        //Look in player's direction:
        Vector2 directionVector = MyMathUtils.getVectorFromTwoPoints(
                enemy.getX(), enemy.getY(), playerCharacter.getX(), playerCharacter.getY());
        enemy.setRotation(CoordinateSystem.getRotationAngleInRadians(directionVector));

        AIActionPerformer aiActionPerformer = enemy.getAIActionPerformer();
        if(!enemyIsPerformingAIAction(enemy)){
            aiActionPerformer = getNewAIActionPerformer(enemy, incomingArrow, playerCharacter);
        }
        aiActionPerformer.performAction(samuraiWorld);
    }

    private static boolean enemyIsPerformingAIAction(Enemy enemy) {
        return enemy.getAIActionPerformer()!=null && !enemy.getAIActionPerformer().isExpired();
    }

    private static AIActionPerformer getNewAIActionPerformer(Enemy enemy, boolean incomingArrow, PlayerCharacter playerCharacter) {
        ActionState actionState = null;
        try {
            float distanceToPlayer = MyMathUtils.getDistance(playerCharacter.getX(), playerCharacter.getY(),
                    enemy.getX(), enemy.getY());
//                actionState = getNewEnemyAIActionState(playerCharacter.getState(), distanceToPlayer);
            //TODO Replace with call to groovy script (above)?
            actionState = EnemyAIActionScript.getNewEnemyAIActionState(playerCharacter.getState(), distanceToPlayer,
                    incomingArrow);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return AIActionPerformerProvider.getActionPerformer(actionState, enemy);
    }

    //TODO Don't remove
    public static ActionState getNewEnemyAIActionState(State playerState, float distanceToPlayer) throws Exception {
        Script<String, String> script = new Script<String, String>("EnemyAIAction.groovy");
        script.setBinding("playerState", playerState.name());
        script.setBinding("distanceToPlayer", distanceToPlayer);

        GroovyManager manager = new GroovyManager();
        manager.execute(script);

        return ActionState.valueOf(script.getResult());
    }

    /**
     * Checks for collisions between the enemy's field of vision
     * and any existing arrows
     */
    public static boolean arrowIsIncoming(SamuraiWorld samuraiWorld, Enemy enemy){
        World physicalWorld = samuraiWorld.getPhysicalWorld();
        Fixture enemyFOV = PhysicalWorldHelper.getFieldOfVisionFieldFor(enemy, physicalWorld);
        for(Contact contact : physicalWorld.getContactList()){
            if(contact.getFixtureA().equals(enemyFOV) || contact.getFixtureB().equals(enemyFOV)){
                if(contact.isTouching()){
                    if(PhysicalWorldHelper.isBetweenArrowAndFOV(contact)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean anyEnemyIsAwareOfPlayer(SamuraiWorld samuraiWorld){
        for(Enemy enemy : samuraiWorld.getEnemies()){
            if(enemy.isPlayerAware() && enemy.isAlive()){
                return true;
            }
        }
        return false;
    }

    public static void handlePlayerHealthRegen(SamuraiWorld samuraiWorld) {
        if(!anyEnemyIsAwareOfPlayer(samuraiWorld)){
            samuraiWorld.getPlayerCharacter().heal(HEALTH_REGEN_SPEED);
        }
    }
}
