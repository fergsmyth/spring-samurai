package com.genericgames.samurai.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.ai.routefinding.AStar;
import com.genericgames.samurai.ai.routefinding.MapNode;
import com.genericgames.samurai.ai.routefinding.Route;
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
                //Look in player's direction:
                Vector2 directionVector =  new Vector2(playerCharacter.getX() - enemy.getX(),
                        enemy.getY() - playerCharacter.getY());


                MovementVector movementVector;
                if(PhysicalWorldHelper.clearLineBetween(playerCharacter, enemy, physicalWorld)){
                    movementVector = new MovementVector(directionVector);
                }
                else {
                    if(enemy.getRoute().getMapNodes().isEmpty() || enemy.getRoute().isStale()){
                        AStar aStar = new AStar(enemy.getX(), enemy.getY(),
                                playerCharacter.getX(), playerCharacter.getY(),
                                samuraiWorld.getCurrentLevel().getRoutingFindingRouteCostMap());
                        enemy.getRoute().setMapNodes(aStar.findPath());
                    }
                    MapNode mapNode = AIHelper.getNextRouteNode(enemy, physicalWorld);

                    movementVector = new MovementVector(new Vector2((mapNode.getPositionX() + 0.5f) - enemy.getX(),
                            enemy.getY() - (mapNode.getPositionY() + 0.5f)));
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
     * and return the first one found which the ai has a clear line of sight to.
     * @param ai
     * @return
     */
    private static MapNode getNextRouteNode(AI ai, World physicalWorld) {
        MapNode selectedNode = null;
        List<MapNode> toBeRemoved = new ArrayList<MapNode>();
        Route route = ai.getRoute();
        route.incrementLifeTime();
        for(MapNode mapNode : route.getMapNodes()){
            //Remove all unnecessary nodes. i.e. the ones BEFORE the node in the route that was selected:
            if(selectedNode != null){
                toBeRemoved.add(mapNode);
            }

            //To take shortcuts, and prevent AI walking robotically, in strictly 90 degree angles only.
            if(PhysicalWorldHelper.clearLineBetween(mapNode.getPositionX() + 0.5f, mapNode.getPositionY() + 0.5f,
                    ai.getX(), ai.getY(), physicalWorld)){
                selectedNode = mapNode;
            }
        }
        route.getMapNodes().removeAll(toBeRemoved);
        return selectedNode;
    }
}
