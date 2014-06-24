package com.genericgames.samurai.ai.routefinding;

import com.genericgames.samurai.maths.MyMathUtils;

import java.util.*;

public class AStar {

    private AStarNode startNode;
    private AStarNode targetNode;

    private RouteCostMap routeCostMap;
    private Map<Float, Map<Float, AStarNode>> allNodes;
    private Map<Float, Map<Float, AStarNode>> closedList;
    private Map<Float, Map<Float, AStarNode>> openList;
    private Map<AStarNode, AStarNode> cameFrom;

    public AStar(float startPositionX, float startPositionY, float targetPositionX, float targetPositionY,
                 RouteCostMap routeCostMap){
        this.routeCostMap = routeCostMap;
        createNodes(startPositionX, startPositionY, targetPositionX, targetPositionY);
        closedList = new HashMap<Float, Map<Float, AStarNode>>();
        openList = new HashMap<Float, Map<Float, AStarNode>>();
        cameFrom = new HashMap<AStarNode, AStarNode>();
    }

    private void createNodes(float startPositionX, float startPositionY, float targetPositionX, float targetPositionY) {
        startPositionX = RouteFindingHelper.roundToNearestTile(startPositionX);
        startPositionY = RouteFindingHelper.roundToNearestTile(startPositionY);
        targetPositionX = RouteFindingHelper.roundToNearestTile(targetPositionX);
        targetPositionY = RouteFindingHelper.roundToNearestTile(targetPositionY);

        allNodes = new HashMap<Float, Map<Float, AStarNode>>();
        for(MapNode mapNode : routeCostMap.getNodes()){
            AStarNode aStarNode = new AStarNode(mapNode);
            aStarNode.setH(MyMathUtils.getDistance(mapNode.getCost() +
                    mapNode.getPositionX(), mapNode.getPositionY(),
                    targetPositionX, targetPositionY));

            // if targetNode:
            if(mapNode.getPositionX()==targetPositionX && mapNode.getPositionY()==targetPositionY){
                setTargetNode(aStarNode);
            }
            // else if startNode:
            else if(mapNode.getPositionX()==startPositionX && mapNode.getPositionY()==startPositionY){
                setStartNode(aStarNode);
            }
            addNodeToMap(aStarNode, allNodes);
        }
    }

    private void setStartNode(AStarNode startNode) {
        this.startNode = startNode;
    }

    private void setTargetNode(AStarNode targetNode) {
        this.targetNode = targetNode;
    }

    /**
     * Called until AStar.pathComplete() indicates a final path has been found.
     * returns
     */
    public List<MapNode> findPath(){
        addNodeToMap(startNode, openList);

        startNode.setG(0f);
        startNode.setF(startNode.getG() + startNode.getH()) ;

        while(!openList.isEmpty()){
            AStarNode currentNode = getOpenNodeWithLowestF();
            if(currentNode.equals(targetNode)){
                return reconstructPath(cameFrom, targetNode);
            }

            removeNodeFromMap(currentNode, openList);
            addNodeToMap(currentNode, closedList);
            for(AStarNode neighbour : getNeighbouringNodes(currentNode)){
                if(mapContainsNode(neighbour, closedList)){
                    continue;
                }
                float tentativeGScore = currentNode.getG() +
                        MyMathUtils.getDistance(currentNode, neighbour);

                if((!mapContainsNode(neighbour, openList)) || (tentativeGScore < neighbour.getG())){
                    cameFrom.put(neighbour, currentNode);
                    neighbour.setG(tentativeGScore);
                    neighbour.setF(neighbour.getG() + neighbour.getH());

                    addNodeToMap(neighbour, openList);
                }
            }
        }
        return new ArrayList<MapNode>();
    }

    private List<MapNode> reconstructPath(Map<AStarNode, AStarNode> cameFrom, AStarNode node) {
        List<MapNode> path = new ArrayList<MapNode>();
        while(cameFrom.containsKey(node)){
            path.add(node.getMapNode());

            node = cameFrom.get(node);
        }

        if(!path.contains(startNode.getMapNode())){
            path.add(startNode.getMapNode());
        }

        return path;
    }

    private AStarNode getOpenNodeWithLowestF() {
        AStarNode nodeWithLowestF = null;
        for(Map<Float, AStarNode> innerMap : openList.values()){
            for(AStarNode currentNode : innerMap.values()){
                if(nodeWithLowestF == null || currentNode.getF() < nodeWithLowestF.getF()){
                    nodeWithLowestF = currentNode;
                }
            }
        }
        return nodeWithLowestF;
    }

    private Collection<AStarNode> getNeighbouringNodes(AStarNode node) {
        Collection<AStarNode> neighbours = new ArrayList<AStarNode>();
        float nodePositionX = node.getMapNode().getPositionX();
        float nodePositionY = node.getMapNode().getPositionY();

        //Get Above Neighbour:
        AStarNode neighbour = allNodes.get(nodePositionX).get(nodePositionY + 1);
        if(neighbour != null){
            neighbours.add(neighbour);
        }

        //Get Below Neighbour:
        neighbour = allNodes.get(nodePositionX).get(nodePositionY - 1);
        if(neighbour != null){
            neighbours.add(neighbour);
        }

        Map<Float, AStarNode> yNodes = allNodes.get(nodePositionX+1);
        if( yNodes!=null && yNodes.get(nodePositionY)!=null){
            //Get Right Neighbour:
            neighbours.add(yNodes.get(nodePositionY));
        }

        yNodes = allNodes.get(nodePositionX-1);
        if( yNodes!=null && yNodes.get(nodePositionY)!=null){
            //Get Left Neighbour:
            neighbours.add(yNodes.get(nodePositionY));
        }
        return neighbours;
    }

    private void addNodeToMap(AStarNode aStarNode, Map<Float, Map<Float, AStarNode>> map){
        float nodePositionX = aStarNode.getMapNode().getPositionX();
        float nodePositionY = aStarNode.getMapNode().getPositionY();
        if(map.get(nodePositionX) == null){
            map.put(nodePositionX, new HashMap<Float, AStarNode>());
        }
        map.get(nodePositionX).put(nodePositionY, aStarNode);
    }

    private void removeNodeFromMap(AStarNode aStarNode, Map<Float, Map<Float, AStarNode>> map){
        map.get(aStarNode.getMapNode().getPositionX()).remove(aStarNode.getMapNode().getPositionY());
    }

    private boolean mapContainsNode(AStarNode aStarNode, Map<Float, Map<Float, AStarNode>> map){
        Map<Float, AStarNode> yNodes = map.get(aStarNode.getMapNode().getPositionX());
        return yNodes!=null &&
                yNodes.get(aStarNode.getMapNode().getPositionY())!=null;
    }
}
