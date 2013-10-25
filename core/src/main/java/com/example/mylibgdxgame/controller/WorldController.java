package com.example.mylibgdxgame.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.example.mylibgdxgame.model.MyWorld;
import com.example.mylibgdxgame.model.movable.State;
import com.example.mylibgdxgame.physics.PhysicalWorldHelper;

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

    // ** Key presses and touches **************** //
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

    /** The main update method **/
    public void update() {
        processInput();
    }

    /** Change Bob's state and parameters based on input controls **/
    private void processInput() {
		float velocityX = 0;
		float velocityY = 0;
        if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
                (!keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT))){
			velocityX = 0;
        }
        else if (keys.get(Keys.LEFT)) {
			// left is pressed
			velocityX = -1*myWorld.getPlayerCharacter().getSpeed();
		}
		else if (keys.get(Keys.RIGHT)) {
			// right is pressed
			velocityX = myWorld.getPlayerCharacter().getSpeed();
		}

        if ((keys.get(Keys.UP) && keys.get(Keys.DOWN)) ||
                (!keys.get(Keys.UP) && !keys.get(Keys.DOWN))){
			velocityY = 0;
        }
        else if (keys.get(Keys.UP)) {
			// up is pressed
			velocityY = myWorld.getPlayerCharacter().getSpeed();
		}
		else if (keys.get(Keys.DOWN)) {
			// down is pressed
			velocityY = -1*myWorld.getPlayerCharacter().getSpeed();
		}

		if(velocityX != 0 || velocityY != 0){
			myWorld.getPlayerCharacter().setState(State.WALKING);
			myWorld.getPlayerCharacter().setStateTime(myWorld.getPlayerCharacter().getStateTime()+1);
		}
		else{
			myWorld.getPlayerCharacter().setState(State.IDLE);
		}

		PhysicalWorldHelper.moveBody(myWorld.getPhysicalWorld(), myWorld.getPlayerCharacter(), directionVector, velocityX, velocityY);

//        //if moving diagonally:
//        if(character.getVelocityX() != 0 && character.getVelocityY() != 0){
//            int directionalVelocity = (int) Math.round(Math.sqrt(
//                    (character.getVelocityX()*character.getVelocityX())/2));
//
//            //Find the diagonal velocity (via Pythagoras' theorem):
//            character.setVelocityX(Integer.signum(character.getVelocityX())*directionalVelocity);
//            character.setVelocityY(Integer.signum(character.getVelocityY())*directionalVelocity);
//        }
    }
}
