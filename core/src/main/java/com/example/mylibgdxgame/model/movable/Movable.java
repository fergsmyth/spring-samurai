package com.example.mylibgdxgame.model.movable;

import com.example.mylibgdxgame.model.WorldObject;

/**
 * Any movable object, e.g. projectiles, vehicles, creatures, characters (including playable)
 */
public abstract class Movable extends WorldObject {

    private static final int DEFAULT_SPEED = 1;

    private int velocityX;

    private int velocityY;
    // a constant dictating the speed at which the object moves (when it does):
    private int speed;
    // true if the object always faces the direction in which it is travelling. Otherwise, false.
    private boolean directionFacing = true;

    public Movable(){
        super();
        this.setSpeed(DEFAULT_SPEED);
        setVelocity(0, 0);
    }

    public Movable(int speed) {
        super();
        setSpeed(speed);
        setVelocity(0, 0);
    }

    public Movable(int positonX, int positonY, int direction){
        super(positonX, positonY, direction);
        setVelocity(0, 0);
    }

    public Movable(int positonX, int positonY, int direction, int velocityX, int velocityY){
        super(positonX, positonY, direction);
        setVelocity(velocityX, velocityY);
    }

    public boolean isMoving(){
        if (velocityX != 0 || velocityY != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public void setVelocity(int velocityX, int velocityY){
        setVelocityX(velocityX);
        setVelocityY(velocityY);
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public void moveLeft(){
        velocityX = -1*speed;
        updatePosition();
        if(directionFacing){
            setDirection(270);
        }
    }

    public void moveRight(){
        velocityX = speed;
        updatePosition();
        if(directionFacing){
            setDirection(90);
        }
    }

    public void moveUp(){
        velocityY = speed;
        updatePosition();
        if(directionFacing){
            setDirection(0);
        }
    }

    public void moveDown(){
        velocityY = -1*speed;
        updatePosition();
        if(directionFacing){
            setDirection(180);
        }
    }

    private void updatePosition() {
        setPositionX(getPositionX() + velocityX);
        setPositionY(getPositionY() + velocityY);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isDirectionFacing() {
        return directionFacing;
    }

    public void setDirectionFacing(boolean directionFacing) {
        this.directionFacing = directionFacing;
    }
}
