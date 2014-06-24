package com.genericgames.samurai.ai.routefinding;

public class AStarNode {

    // the exact cost to reach this node from the starting node.
    private float g;
    // the estimated(heuristic, Euclidean distance) cost to reach the destination from here.
    private float h;
    // F = G + H
    // As the algorithm runs the F value of a node tells us how expensive
    // we think it will be to reach our goal by way of that node.
    private float f;

    private MapNode mapNode;

    public AStarNode(MapNode mapNode){
        setMapNode(mapNode);
    }

    public void setMapNode(MapNode mapNode) {
        this.mapNode = mapNode;
    }

    public MapNode getMapNode() {
        return mapNode;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }
}