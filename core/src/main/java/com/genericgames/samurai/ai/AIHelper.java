package com.genericgames.samurai.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.ai.performers.AIActionPerformer;
import com.genericgames.samurai.ai.performers.AIActionPerformerProvider;
import com.genericgames.samurai.ai.routefinding.*;
import com.genericgames.samurai.maths.MyMathUtils;
import com.genericgames.samurai.model.PlayerCharacter;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.ai.AI;
import com.genericgames.samurai.model.movable.living.ai.ActionState;
import com.genericgames.samurai.model.movable.living.ai.Enemy;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.CoordinateSystem;
import com.genericgames.samurai.utility.MovementVector;

import java.util.Arrays;
import java.util.Random;

public class AIHelper {

    private static final Random RANDOM = new Random();

    /**
     * Checks if a player is within an enemy's field of vision.
     * If he is, then check if there's a clear line of sight between the two.
     */
    public static void detectAIAwareness(SamuraiWorld samuraiWorld){
        World physicalWorld = samuraiWorld.getPhysicalWorld();
        for(Contact contact : physicalWorld.getContactList()){
            if(contact.isTouching()){
                if(PhysicalWorldHelper.isBetweenPlayerAndEnemyFOV(contact)){
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
     * If he is, then check if there's a clear path  the two.
     */
    public static boolean isEnemyInCombat(SamuraiWorld samuraiWorld, Enemy enemy){
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
                if(isEnemyInCombat(samuraiWorld, enemy)){
                    performRandomCombatAction(samuraiWorld, enemy);
                }
                else {
                    performRouteFindingToPlayer(samuraiWorld, enemy);
                }
            }
        }
    }

    private static void performRouteFindingToPlayer(SamuraiWorld samuraiWorld, AI ai) {
        if(ai.isPlayerAware() && ai.isAlive()){
            PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
            World physicalWorld = samuraiWorld.getPhysicalWorld();

            Vector2 directionVector;
            if(PhysicalWorldHelper.clearPathBetween(ai, playerCharacter, physicalWorld)){
                ai.getRoute().setStale(true);
                //Look in player's direction:
                directionVector = MyMathUtils.getVectorFromTwoPoints(
                        ai.getX(), ai.getY(), playerCharacter.getX(), playerCharacter.getY());
            }
            else {
                if(ai.getRoute()==null || ai.getRoute().getMapNodes().isEmpty() || ai.getRoute().isStale()){
                    RouteCostMap upToDateRouteCostMap = RouteFindingHelper.getUpToDateRouteCostMap(samuraiWorld.getCurrentLevel());
                    AStar aStar = new AStar(ai.getX(), ai.getY(),
                            playerCharacter.getX(), playerCharacter.getY(),
                            upToDateRouteCostMap);
                    ai.setRoute(new Route(aStar.findPath()));
                }
                RouteFindingHelper.getNextRouteNode(ai, physicalWorld);
                MapNode mapNode = ai.getRoute().getCurrentTargetNode();

                float targetX = mapNode.getPositionX() + 0.5f;
                float targetY = mapNode.getPositionY() + 0.5f;
                directionVector = MyMathUtils.getVectorFromTwoPoints(
                        ai.getX(), ai.getY(), targetX, targetY);
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
    }

    private static void performRandomCombatAction(SamuraiWorld samuraiWorld, Enemy enemy) {
        //TODO Remove test code:
        PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
        //Look in player's direction:
        Vector2 directionVector = MyMathUtils.getVectorFromTwoPoints(
                enemy.getX(), enemy.getY(), playerCharacter.getX(), playerCharacter.getY());
        enemy.setRotation(CoordinateSystem.getRotationAngleInRadians(directionVector));

        AIActionPerformer aiActionPerformer = enemy.getAIActionPerformer();
        if(aiActionPerformer== null ||
                aiActionPerformer.getActionState().equals(ActionState.IDLE)){

            ActionState randomActionState = Arrays.asList(ActionState.values())
                    .get(RANDOM.nextInt(ActionState.values().length-1));
            aiActionPerformer = AIActionPerformerProvider.getActionPerformer(randomActionState, enemy);
        }
        aiActionPerformer.performAction(samuraiWorld);
    }
}
