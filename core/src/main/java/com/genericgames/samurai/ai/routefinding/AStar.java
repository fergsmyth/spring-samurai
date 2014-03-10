package com.genericgames.samurai.ai.routefinding;

import com.genericgames.samurai.maths.MyMathUtils;

import java.util.*;

public class AStar {

    private AStarNode startNode;
    private AStarNode targetNode;

    private RouteCostMap routeCostMap;
    private Collection<AStarNode> allNodes;
    private Collection<AStarNode> closedList;
    private Collection<AStarNode> openList;
    private Map<AStarNode, AStarNode> cameFrom;

    public AStar(float startPositionX, float startPositionY, float targetPositionX, float targetPositionY,
                 RouteCostMap routeCostMap){
        this.routeCostMap = routeCostMap;
        createNodes(startPositionX, startPositionY, targetPositionX, targetPositionY);
        closedList = new ArrayList<AStarNode>();
        openList = new ArrayList<AStarNode>();
        cameFrom = new HashMap<AStarNode, AStarNode>();
    }

    private void createNodes(float startPositionX, float startPositionY, float targetPositionX, float targetPositionY) {
        startPositionX = RouteFindingHelper.roundToNearestTile(startPositionX);
        startPositionY = RouteFindingHelper.roundToNearestTile(startPositionY);
        targetPositionX = RouteFindingHelper.roundToNearestTile(targetPositionX);
        targetPositionY = RouteFindingHelper.roundToNearestTile(targetPositionY);

        allNodes = new ArrayList<AStarNode>();
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
            allNodes.add(aStarNode);
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
        openList.add(startNode);

        startNode.setG(0f);
        startNode.setF(startNode.getG() + startNode.getH()) ;

        while(!openList.isEmpty()){
            AStarNode currentNode = getOpenNodeWithLowestF();
            if(currentNode.equals(targetNode)){
                return reconstructPath(cameFrom, targetNode);
            }

            openList.remove(currentNode);
            closedList.add(currentNode);
            for(AStarNode neighbour : getNeighbouringNodes(currentNode)){
                if(closedList.contains(neighbour)){
                    continue;
                }
                float tentativeGScore = currentNode.getG() +
                        MyMathUtils.getDistance(currentNode, neighbour);

                if((!openList.contains(neighbour)) || (tentativeGScore < neighbour.getG())){
                    cameFrom.put(neighbour, currentNode);
                    neighbour.setG(tentativeGScore);
                    neighbour.setF(neighbour.getG() + neighbour.getH());
                    if(!openList.contains(neighbour)){
                        openList.add(neighbour);
                    }
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
        for(AStarNode currentNode : openList){
            if(nodeWithLowestF == null || currentNode.getF() < nodeWithLowestF.getF()){
                nodeWithLowestF = currentNode;
            }
        }
        return nodeWithLowestF;
    }

    private Collection<AStarNode> getNeighbouringNodes(AStarNode node) {
        Collection<AStarNode> neighbours = new ArrayList<AStarNode>();
        for(AStarNode potentialNeighbour : allNodes){
            if(areNeighbors(node, potentialNeighbour)){
                neighbours.add(potentialNeighbour);
            }
        }
        return neighbours;
    }

    private boolean areNeighbors(AStarNode currentNode, AStarNode potentialNeighbour) {
        return currentNode.isAboveNeighbour(potentialNeighbour) || currentNode.isBelowNeighbour(potentialNeighbour) ||
                currentNode.isRightNeighbour(potentialNeighbour) || currentNode.isLeftNeighbour(potentialNeighbour);
    }
}
