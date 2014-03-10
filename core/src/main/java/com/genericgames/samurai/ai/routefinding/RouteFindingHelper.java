package com.genericgames.samurai.ai.routefinding;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.maths.MyMathUtils;
import com.genericgames.samurai.model.Level;
import com.genericgames.samurai.model.movable.living.ai.AI;
import com.genericgames.samurai.model.movable.living.ai.NPC;
import com.genericgames.samurai.physics.PhysicalWorldHelper;

import java.util.ArrayList;
import java.util.List;

public class RouteFindingHelper {

    private static int collidableNodeCost = 1000;

    public static RouteCostMap getUpToDateRouteCostMap(Level level) {
        RouteCostMap upToDateMap = level.getRoutingFindingRouteCostMap().copy();
        for(MapNode node : upToDateMap.getNodes()){
            for(NPC npc : level.getNPCs()){
                if(roundToNearestTile(npc.getX()) == node.getPositionX() &&
                        roundToNearestTile(npc.getY()) == node.getPositionY()){
                    node.setCost(getCollidableNodeCost());
                }
            }
        }
        return upToDateMap;
    }

    /**
     * Iterate from last to first node in route node list,
     * and sets the first one found which the ai has a clear line of sight to.
     * @param ai
     */
    public static void getNextRouteNode(AI ai, World physicalWorld) {
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
                else if(PhysicalWorldHelper.clearPathBetween(ai,
                        mapNode.getPositionX() + 0.5f, mapNode.getPositionY() + 0.5f, new ArrayList<Fixture>(),
                        physicalWorld)){
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
                ai.getX(), ai.getY()) < 0.05f;
    }

    public static float roundToNearestTile(float positionCoord) {
        return (float) StrictMath.floor(positionCoord);
    }

    public static int getCollidableNodeCost() {
        return collidableNodeCost;
    }
}
