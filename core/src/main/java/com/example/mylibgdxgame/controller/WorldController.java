package com.example.mylibgdxgame.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.mylibgdxgame.model.MyWorld;

public class WorldController {

    enum Keys {
        LEFT, RIGHT, UP, DOWN
    }

    private MyWorld myWorld;

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
			velocityX = -1*myWorld.getKing().getSpeed();
		}
		else if (keys.get(Keys.RIGHT)) {
			// right is pressed
			velocityX = myWorld.getKing().getSpeed();
		}

        if ((keys.get(Keys.UP) && keys.get(Keys.DOWN)) ||
                (!keys.get(Keys.UP) && !keys.get(Keys.DOWN))){
			velocityY = 0;
        }
        else if (keys.get(Keys.UP)) {
			// up is pressed
			velocityY = myWorld.getKing().getSpeed();
		}
		else if (keys.get(Keys.DOWN)) {
			// down is pressed
			velocityY = -1*myWorld.getKing().getSpeed();
		}

		myWorld.moveBody(myWorld.getKing(), velocityX, velocityY);

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
