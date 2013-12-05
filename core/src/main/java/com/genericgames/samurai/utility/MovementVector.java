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
        Vector2 movementVector = CoordinateSystem.translateMouseToLocalPosition(directionVector);
        movementVector.y = -movementVector.y;
        return movementVector;
    }

    public void forwardMovement(){
        this.add(getMovementDirection());
        printDebug("Forward");
    }

    public void backwardMovement(){
        this.add(getMovementDirection());
        y = -y;
        x  = -x;
        printDebug("Backward");
    }

    public void leftMovement(){
        this.add(getMovementDirection().rotate(90));
        printDebug("Left");
    }

    public void rightMovement(){
        this.add(getMovementDirection().rotate(270));
        printDebug("Right");
    }

    public Vector2 getMovementVector(){
        return this.nor().scl(DEFAULT_SPEED);
    }

    public Vector2 getLightAttackVector(){
        forwardMovement();
        return this.nor().scl(0f);
    }

    public Vector2 getHeavyAttackVector(){
        forwardMovement();
        return this.nor().scl(2*DEFAULT_SPEED);
    }

    public String toString(){
        return "Movement Vector : [x : " + x + ", y : " + y + "]";
    }

    private void printDebug(String direction) {
        if(DebugMode.isDebugEnabled()){
            Gdx.app.log(direction, this.toString());
        }
    }

    public void stop() {
        this.nor().scl(0f);
    }
}
