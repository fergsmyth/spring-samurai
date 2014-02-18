package com.genericgames.samurai.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.ai.routefinding.AStar;
import com.genericgames.samurai.ai.routefinding.MapNode;
import com.genericgames.samurai.ai.routefinding.Route;
import com.genericgames.samurai.maths.MyMathUtils;
import com.genericgames.samurai.model.PlayerCharacter;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.ai.AI;
import com.genericgames.samurai.model.movable.living.ai.Enemy;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.MovementVector;

import java.util.ArrayList;
import java.util.List;

public class AIHelper {

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
        //TODO handle AI combat
        handleAIMovement(samuraiWorld);
    }

    private static void handleAIMovement(SamuraiWorld samuraiWorld) {
        for(Enemy enemy : samuraiWorld.getEnemies()){
            if(enemy.isPlayerAware() && enemy.isAlive()){
                PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
                World physicalWorld = samuraiWorld.getPhysicalWorld();


                Vector2 directionVector;
                MovementVector movementVector;
                if(PhysicalWorldHelper.clearLineBetween(playerCharacter, enemy, physicalWorld)){
                    enemy.setRoute(null);
                    //Look in player's direction:
                    directionVector =  new Vector2(playerCharacter.getX() - enemy.getX(),
                            enemy.getY() - playerCharacter.getY());
                    movementVector = new MovementVector(directionVector);
                }
                else {
                    if(enemy.getRoute()==null || enemy.getRoute().getMapNodes().isEmpty() || enemy.getRoute().isStale()){
                        AStar aStar = new AStar(enemy.getX(), enemy.getY(),
                                playerCharacter.getX(), playerCharacter.getY(),
                                samuraiWorld.getCurrentLevel().getRoutingFindingRouteCostMap());
                        enemy.setRoute(new Route(aStar.findPath()));
                    }
                    AIHelper.getNextRouteNode(enemy, physicalWorld);
                    MapNode mapNode = enemy.getRoute().getCurrentTargetNode();

                    float targetX = mapNode.getPositionX() + 0.5f;
                    float targetY = mapNode.getPositionY() + 0.5f;
                    directionVector =  new Vector2(targetX - enemy.getX(),
                            enemy.getY() - targetY);
                    movementVector = new MovementVector(new Vector2(targetX - enemy.getX(),
                            enemy.getY() - targetY));
                }

                movementVector.forwardMovement();

                if(movementVector.hasMoved()){
                    enemy.setState(State.WALKING);
                    enemy.incrementStateTime();
                }
                else {
                    enemy.setState(State.IDLE);
                }

                PhysicalWorldHelper.moveBody(samuraiWorld.getPhysicalWorld(), enemy, directionVector,
                        movementVector.getEnemyMovementVector());
            }
        }
    }

    /**
     * Iterate from last to first node in route node list,
     * and sets the first one found which the ai has a clear line of sight to.
     * @param ai
     */
    private static void getNextRouteNode(AI ai, World physicalWorld) {
        Route route = ai.getRoute();
        List<MapNode> routeMapNodes = route.getMapNodes();
        MapNode prevTargetNode = route.getCurrentTargetNode();

        MapNode selectedNode = null;
        if(prevTargetNode == null || hasBeenReached(prevTargetNode, ai)){
            List<MapNode> toBeRemoved = new ArrayList<MapNode>();
            for(MapNode mapNode : routeMapNodes){
                //Remove all unnecessary nodes. i.e. the ones BEFORE the node in the route that was selected:
                if(selectedNode != null){
                    toBeRemoved.add(mapNode);
                }
                //To take shortcuts, and prevent AI walking robotically, in strictly 90 degree angles only.
                else if(PhysicalWorldHelper.clearLineBetween(mapNode.getPositionX() + 0.5f, mapNode.getPositionY() + 0.5f,
                        ai.getX(), ai.getY(), physicalWorld)){
                    selectedNode = mapNode;
                }
            }
            routeMapNodes.removeAll(toBeRemoved);
        }
        else {
            selectedNode = prevTargetNode;
        }

        route.incrementLifeTime();
        route.setCurrentTargetNode(selectedNode);
    }

    private static boolean hasBeenReached(MapNode targetNode, AI ai) {
        return MyMathUtils.getDistance(targetNode.getPositionX() + 0.5f, targetNode.getPositionY() + 0.5f,
                ai.getX(), ai.getY()) < 0.03f;
    }
}
