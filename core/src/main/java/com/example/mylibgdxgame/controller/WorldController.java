package com.example.mylibgdxgame.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.example.mylibgdxgame.model.MyWorld;
import com.example.mylibgdxgame.model.movable.State;
import com.example.mylibgdxgame.physics.PhysicalWorld;
import com.example.mylibgdxgame.view.CoordinateSystem;

public class WorldController {

    enum Keys {
        LEFT, RIGHT, FORWARD, BACKWARD
    }

    private MyWorld myWorld;
    private Vector2 directionVector = new Vector2();
    static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();


    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.FORWARD, false);
        keys.put(Keys.BACKWARD, false);
    };

    public WorldController(MyWorld myWorld) {
        this.myWorld = myWorld;
    }

    public void leftPressed() {
        keys.get(keys.put(Keys.LEFT, true));
    }

    public void rightPressed() {
        keys.get(keys.put(Keys.RIGHT, true));
    }

    public void upPressed() {
        keys.get(keys.put(Keys.FORWARD, true));
    }

    public void downPressed() {
        keys.get(keys.put(Keys.BACKWARD, true));
    }

    public void leftReleased() {
        keys.get(keys.put(Keys.LEFT, false));
    }

    public void rightReleased() {
        keys.get(keys.put(Keys.RIGHT, false));
    }

    public void upReleased() {
        keys.get(keys.put(Keys.FORWARD, false));
    }

    public void downReleased() {
        keys.get(keys.put(Keys.BACKWARD, false));
    }

    public void setDirectionVector(float x, float y){
        directionVector = new Vector2(x, y);
    }

    public Vector2 getNormalisedDirection(){
        return CoordinateSystem.translateMousePosToWorldPosition(directionVector).nor();
    }

    public Vector2 getRotatedNormalisedDirection(float degrees){
        return getNormalisedDirection().rotate(degrees);
    }

    public void update() {
        processInput();
    }

    private void processInput() {
		float velocityX = 0;
		float velocityY = 0;
        Vector2 linearVelocity = new Vector2();
        if (keys.get(Keys.LEFT)) {
//            Vector2 normVector = getRotatedNormalisedDirection(270);
//            velocityX = normVector.x * myWorld.getPlayerCharacter().getSpeed();
//            velocityY = normVector.y * myWorld.getPlayerCharacter().getSpeed();
//            linearVelocity.add(velocityX, velocityY);
		} else if (keys.get(Keys.RIGHT)) {
//            Vector2 normVector = getRotatedNormalisedDirection(90);
//            velocityX = normVector.x * myWorld.getPlayerCharacter().getSpeed();
//            velocityY = normVector.y * myWorld.getPlayerCharacter().getSpeed();
//            linearVelocity.add(velocityX, velocityY);
		}

        if (keys.get(Keys.FORWARD)) {
            velocityX = getNormalisedDirection().x * myWorld.getPlayerCharacter().getSpeed();
            velocityY = (-getNormalisedDirection().y) * myWorld.getPlayerCharacter().getSpeed();
            linearVelocity.add(velocityX, velocityY);
        }
		else if (keys.get(Keys.BACKWARD)) {
            velocityX = (-getNormalisedDirection().x) * myWorld.getPlayerCharacter().getSpeed();
            velocityY = getNormalisedDirection().y * myWorld.getPlayerCharacter().getSpeed();
            linearVelocity.add(velocityX, velocityY);
		}

		if(velocityX != 0 || velocityY != 0){
			myWorld.getPlayerCharacter().setState(State.WALKING);
			myWorld.getPlayerCharacter().setStateTime(myWorld.getPlayerCharacter().getStateTime()+1);
		}
		else{
			myWorld.getPlayerCharacter().setState(State.IDLE);
		}

		PhysicalWorld.moveBody(myWorld.getPhysicalWorld(), myWorld.getPlayerCharacter(), directionVector, linearVelocity);

    }
}
