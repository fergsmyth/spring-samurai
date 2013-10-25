package com.example.mylibgdxgame.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.example.mylibgdxgame.model.MyWorld;
import com.example.mylibgdxgame.model.movable.State;
import com.example.mylibgdxgame.physics.PhysicalWorldHelper;
import com.example.mylibgdxgame.view.CoordinateSystem;

public class WorldController {

    enum Keys {
        LEFT, RIGHT, UP, DOWN
    }

    private MyWorld myWorld;
    private Vector2 directionVector = new Vector2();
    static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();

    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.UP, false);
        keys.put(Keys.DOWN, false);
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
        keys.get(keys.put(Keys.UP, true));
    }

    public void downPressed() {
        keys.get(keys.put(Keys.DOWN, true));
    }

    public void leftReleased() {
        keys.get(keys.put(Keys.LEFT, false));
    }

    public void rightReleased() {
        keys.get(keys.put(Keys.RIGHT, false));
    }

    public void upReleased() {
        keys.get(keys.put(Keys.UP, false));
    }

    public void downReleased() {
        keys.get(keys.put(Keys.DOWN, false));
    }

    public void setDirectionVector(float x, float y){
        directionVector = new Vector2(x, y);
    }

    public Vector2 getNormalisedDirection(){
        Vector2 convertedVector = CoordinateSystem.translateMousePosToWorldPosition(directionVector);
        return convertedVector.nor();
    }

    public void update() {
        processInput();
    }

    private void processInput() {
		float velocityX = 0;
		float velocityY = 0;

        if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
                (!keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT))){
			velocityX = 0;
        }
        else if (keys.get(Keys.LEFT)) {
//            velocityX = getNormalisedDirection().x * myWorld.getPlayerCharacter().getSpeed();
//            velocityY = (getNormalisedDirection().y) * myWorld.getPlayerCharacter().getSpeed();
		}
		else if (keys.get(Keys.RIGHT)) {
//            velocityX = -getNormalisedDirection().x * myWorld.getPlayerCharacter().getSpeed();
//            velocityY = (-getNormalisedDirection().y) * myWorld.getPlayerCharacter().getSpeed();
		}

        if ((keys.get(Keys.UP) && keys.get(Keys.DOWN)) ||
                (!keys.get(Keys.UP) && !keys.get(Keys.DOWN))){
			velocityY = 0;
        }
        else if (keys.get(Keys.UP)) {
            velocityX = getNormalisedDirection().x * myWorld.getPlayerCharacter().getSpeed();
            velocityY = (-getNormalisedDirection().y) * myWorld.getPlayerCharacter().getSpeed();
		}
		else if (keys.get(Keys.DOWN)) {
            velocityX = (-getNormalisedDirection().x) * myWorld.getPlayerCharacter().getSpeed();
            velocityY = getNormalisedDirection().y * myWorld.getPlayerCharacter().getSpeed();
		}

		if(velocityX != 0 || velocityY != 0){
			myWorld.getPlayerCharacter().setState(State.WALKING);
			myWorld.getPlayerCharacter().setStateTime(myWorld.getPlayerCharacter().getStateTime()+1);
		}
		else{
			myWorld.getPlayerCharacter().setState(State.IDLE);
		}

		PhysicalWorldHelper.moveBody(myWorld.getPhysicalWorld(), myWorld.getPlayerCharacter(), directionVector, velocityX, velocityY);

    }
}
