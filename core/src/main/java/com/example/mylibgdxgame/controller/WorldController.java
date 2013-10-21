package com.example.mylibgdxgame.controller;

import com.example.mylibgdxgame.model.World;
import com.example.mylibgdxgame.model.movable.living.playable.Playable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 28/09/13
 * Time: 20:05
 * To change this template use File | Settings | File Templates.
 */
public class WorldController {

    enum Keys {
        LEFT, RIGHT, UP, DOWN
    }

    private Playable character;

    static Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();

    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.UP, false);
        keys.put(Keys.DOWN, false);

    };

    public WorldController(World world) {
        this.character = world.getKing();
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
        if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
                (!keys.get(Keys.LEFT) && !keys.get(Keys.RIGHT))){
            character.setVelocityX(0);
        }
        else if (!character.isMoving()){
            if (keys.get(Keys.LEFT)) {
                // left is pressed
                character.moveLeft();
            }
            else if (keys.get(Keys.RIGHT)) {
                // right is pressed
                character.moveRight();
            }
        }

        if ((keys.get(Keys.UP) && keys.get(Keys.DOWN)) ||
                (!keys.get(Keys.UP) && !keys.get(Keys.DOWN))){
            character.setVelocityY(0);
        }
        else if (!character.isMoving()){
            if (keys.get(Keys.UP)) {
                // up is pressed
                character.moveUp();
            }
            else if (keys.get(Keys.DOWN)) {
                // down is pressed
                character.moveDown();
            }
        }

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
