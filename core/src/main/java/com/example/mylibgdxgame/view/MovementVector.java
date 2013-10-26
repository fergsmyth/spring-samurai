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
        x  = x * DEFAULT_SPEED;
        y = -y * DEFAULT_SPEED;
        printDebug();
    }

    public void backwardMovement(){
        hasMoved = true;
        x  = -x * DEFAULT_SPEED;
        y = y * DEFAULT_SPEED;
        printDebug();
    }

    public void leftMovement(){

    }

    public void rightMovement(){

    }

    public Vector2 getMovementVector(){
        if (hasMoved){
            return this;
        } else {
            return Vector2.Zero;
        }
    }

    public String toString(){
        return "Movement Vector : [x : " + x + ", y : " + y + "]";
    }

    private void printDebug() {
        if(DebugMode.isDebugEnabled()){
            System.out.println(this);
        }
    }
}
