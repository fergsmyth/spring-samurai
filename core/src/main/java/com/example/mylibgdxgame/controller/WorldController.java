package com.example.mylibgdxgame.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.example.mylibgdxgame.model.MyWorld;
import com.example.mylibgdxgame.model.movable.State;
import com.example.mylibgdxgame.physics.PhysicalWorld;
import com.example.mylibgdxgame.view.CoordinateSystem;
import com.example.mylibgdxgame.view.MovementVector;

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
    }

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

    public void update() {
        processInput();
    }

    private void processInput() {
        MovementVector movementVector = new MovementVector(getNormalisedDirection());
        if (keys.get(Keys.LEFT)) {
            movementVector.leftMovement();
        } else if (keys.get(Keys.RIGHT)) {
            movementVector.rightMovement();
		}

        if (keys.get(Keys.FORWARD)) {
            movementVector.forwardMovement();
        }
		else if (keys.get(Keys.BACKWARD)) {
            movementVector.backwardMovement();
		}

		if(movementVector.hasMoved()){
			myWorld.getPlayerCharacter().setState(State.WALKING);
			myWorld.getPlayerCharacter().setStateTime(myWorld.getPlayerCharacter().getStateTime()+1);
		}
		else{
			myWorld.getPlayerCharacter().setState(State.IDLE);
		}
		PhysicalWorld.moveBody(myWorld.getPhysicalWorld(), myWorld.getPlayerCharacter(), directionVector, movementVector.getMovementVector());

    }
}
