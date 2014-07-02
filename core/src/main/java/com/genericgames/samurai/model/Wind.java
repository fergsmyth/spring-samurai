package com.genericgames.samurai.model;

import com.badlogic.gdx.math.Vector2;

public class Wind {

    private Vector2 direction;
    private float speed;

    public Wind(Vector2 direction, float speed){
        this.direction = direction;
        this.speed = speed;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
