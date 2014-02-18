package com.genericgames.samurai.ai.routefinding;

public class MapNode implements Comparable {

    // the exact cost of moving from an adjacent node to this node.
    private float cost;

    private float positionX;
    private float positionY;

    public MapNode(float positionX, float positionY){
        setPosition(positionX, positionY);
    }

    public void setPosition(float positionX, float positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
