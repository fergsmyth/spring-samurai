package com.genericgames.samurai.model;

import com.badlogic.gdx.math.MathUtils;

/**
 * Any object displayed in the world which has a position and direction which it's facing.
 */
public abstract class WorldObject {
    private float positionX;
    private float positionY;
    private float angle;
    private int direction;

    public WorldObject(){
        this(0, 0, 0);
    }

    public WorldObject(float positionX, float positionY){
        setPositionX(positionX);
        setPositionY(positionY);
    }

    public WorldObject(float positionX, float positionY, int direction){
        setPositionX(positionX);
        setPositionY(positionY);
        setDirection(direction);
    }

    public void setRotation(float angle){
        this.angle = angle;
    }

    public float getRotationInDegrees(){
        return MathUtils.radiansToDegrees * angle;
    }

    public void setPosition(float positonX, float positonY){
        setPositionX(positonX);
        setPositionY(positonY);
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