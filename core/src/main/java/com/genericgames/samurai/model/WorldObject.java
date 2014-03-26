package com.genericgames.samurai.model;

import com.badlogic.gdx.math.MathUtils;

/**
 * Any object displayed in the world which has a position and direction which it's facing.
 */
public abstract class WorldObject {
    private float positionX;
    private float positionY;
    private float angle;

    public WorldObject(){
        this(0, 0);
    }

    public WorldObject(float positionX, float positionY){
        setPositionX(positionX);
        setPositionY(positionY);
    }

    public float getRotation(){
        return angle;
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

    public float getX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }
}