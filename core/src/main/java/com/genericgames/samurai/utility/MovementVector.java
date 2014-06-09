package com.genericgames.samurai.utility;

import com.badlogic.gdx.math.Vector2;

public class MovementVector extends Vector2 {

    private Vector2 directionVector;

    public MovementVector(Vector2 directionVector){
        this.directionVector = directionVector;
    }

    public Vector2 getDirectionVector(){
        return directionVector;
    }
    public boolean hasMoved(){
        return !this.epsilonEquals(Vector2.Zero, 0.1f);
    }

    public Vector2 getMovementDirection(float characterSpeed){
        Vector2 movementVector = directionVector.cpy();
        movementVector.y = -movementVector.y;
        return movementVector.scl(characterSpeed);
    }

    public void forwardMovement(float characterSpeed){
        this.add(getMovementDirection(characterSpeed));
    }

    public void backwardMovement(float characterSpeed){
        this.add(getMovementDirection(characterSpeed).rotate(180));
    }

    public void leftMovement(float characterSpeed){
        this.add(getMovementDirection(characterSpeed).rotate(90));
    }

    public void rightMovement(float characterSpeed){
        this.add(getMovementDirection(characterSpeed).rotate(270));
    }

    public void northMovement(float characterSpeed){
        this.add(0, 1).scl(characterSpeed);
    }

    public void southMovement(float characterSpeed){
        this.add(0, -1).scl(characterSpeed);
    }

    public void westMovement(float characterSpeed){
        this.add(-1, 0).scl(characterSpeed);
    }

    public void eastMovement(float characterSpeed){
        this.add(1, 0).scl(characterSpeed);
    }

    public Vector2 getScaledMovementVector(float characterSpeed){
        return this.nor().scl(characterSpeed);
    }

    public Vector2 getForwardChargeAttackVector(float characterSpeed){
        forwardMovement(characterSpeed);
        return this.nor().scl(2.0f*characterSpeed);
    }

    public Vector2 getStaticAttackVector(float characterSpeed){
        return this.nor().scl(characterSpeed);
    }

    public Vector2 getDodgeVector(float characterSpeed) {
        return this.cpy().nor().scl(2.0f*characterSpeed);
    }

    public String toString(){
        return "Movement Vector : [x : " + x + ", y : " + y + "]";
    }

    private void printDebug(String direction) {
        if(DebugMode.isDebugEnabled()){
//            Gdx.app.log(direction, this.toString());
        }
    }

    public void stop() {
        this.nor().scl(0f);
    }
}
