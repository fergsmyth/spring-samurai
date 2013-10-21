package com.example.mylibgdxgame.model;

/**
 * Any object displayed in the world which has a position and direction which it's facing.
 */
public abstract class WorldObject {
    private int positionX;
    private int positionY;
    private int direction;

    public WorldObject(){
        this(0, 0, 0);
    }

    public WorldObject(int positionX, int positionY){
        setPositionX(positionX);
        setPositionY(positionY);
    }

    public WorldObject(int positionX, int positionY, int direction){
        setPositionX(positionX);
        setPositionY(positionY);
        setDirection(direction);
    }

    public void setPosition(int positonX, int positonY){
        setPositionX(positonX);
        setPositionY(positonY);
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void rotate(int degrees){
        direction = (direction+degrees+360) % 360;
    }
}