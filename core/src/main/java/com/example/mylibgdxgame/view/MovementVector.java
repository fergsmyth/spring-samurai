package com.example.mylibgdxgame.view;

import com.badlogic.gdx.math.Vector2;

public class MovementVector extends Vector2{

    private static final float DEFAULT_SPEED = 2f;
    private boolean hasMoved = false;

    public MovementVector(Vector2 normalisedVector){
        super(normalisedVector);
    }

    public boolean hasMoved(){
        return hasMoved;
    }

    public void forwardMovement(){
        hasMoved = true;
        y = -y;
        printDebug("Forward");
    }

    public void backwardMovement(){
        hasMoved = true;
        x  = -x;
        printDebug("Backward");
    }

    public void leftMovement(){
        hasMoved = true;
        this.rotate(45);
        printDebug("Left");
    }

    public void rightMovement(){
        this.rotate(315);
        printDebug("Right");
    }

    public Vector2 getMovementVector(){
        if (hasMoved){
            return this.scl(DEFAULT_SPEED);
        } else {
            return Vector2.Zero;
        }
    }

    public String toString(){
        return "Movement Vector : [x : " + x + ", y : " + y + "]";
    }

    private void printDebug(String direction) {
        if(DebugMode.isDebugEnabled()){
            System.out.println(direction + " direction : " +this);
        }
    }
}
