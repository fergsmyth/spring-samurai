package com.example.mylibgdxgame.view;

import com.badlogic.gdx.math.Vector2;

public class MovementVector extends Vector2{

    private static final float DEFAULT_SPEED = 2f;
    private boolean hasMoved = false;

    public MovementVector(){
        super(Vector2.Zero);
    }

    public boolean hasMoved(){
        return hasMoved;
    }

    public void forwardMovement(Vector2 forwardVector){
        hasMoved = true;
        this.add(forwardVector);
        y = -y;
        printDebug("Forward");
    }

    public void backwardMovement(Vector2 backwardVector){
        hasMoved = true;
        this.add(backwardVector);
        x  = -x;
        printDebug("Backward");
    }

    public void leftMovement(Vector2 leftVector){

    }

    public void rightMovement(Vector2 rightVector){

    }

    public Vector2 getMovementVector(){
        if (hasMoved){
            return this.nor().scl(DEFAULT_SPEED);
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
