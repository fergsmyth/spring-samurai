package com.genericgames.samurai.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class MovementVector extends Vector2 {

    private static final float DEFAULT_SPEED = 2f;
    private Vector2 directionVector;

    public MovementVector(Vector2 directionVector){
        this.directionVector = directionVector;
    }

    public boolean hasMoved(){
        return !this.epsilonEquals(Vector2.Zero, 0.1f);
    }

    public Vector2 getMovementDirection(){
        return CoordinateSystem.translateMouseToLocalPosition(directionVector);
    }

    public void forwardMovement(){
        this.set(getMovementDirection());
        y = -y;
        printDebug("Forward");
    }

    public void backwardMovement(){
        this.set(getMovementDirection());
        x  = -x;
        printDebug("Backward");
    }

    public Vector2 getMovementVector(){
        return this.nor().scl(DEFAULT_SPEED);
    }

    public String toString(){
        return "Movement Vector : [x : " + x + ", y : " + y + "]";
    }

    private void printDebug(String direction) {
        if(DebugMode.isDebugEnabled()){
            Gdx.app.log(direction, this.toString());
        }
    }
}
