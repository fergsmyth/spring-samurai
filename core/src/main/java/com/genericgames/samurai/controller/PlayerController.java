package com.genericgames.samurai.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.audio.AudioPlayer;
import com.genericgames.samurai.model.MyWorld;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.physics.PhysicalWorld;
import com.genericgames.samurai.view.CoordinateSystem;
import com.genericgames.samurai.view.DebugMode;
import com.genericgames.samurai.view.MovementVector;

public class PlayerController extends InputAdapter {

    private MyWorld myWorld;
    private Vector2 directionVector = new Vector2();
    private Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();

    public PlayerController(MyWorld myWorld) {
        this.myWorld = myWorld;
        initializeKeyMap();
    }

    public enum Keys {
        LEFT(Input.Keys.A), RIGHT(Input.Keys.D), FORWARD(Input.Keys.W), BACKWARD(Input.Keys.S);
        private int keycode;
        private Keys(int keycode){
            this.keycode = keycode;
        }

        public static boolean contains(int keycode){
            for (Keys key : Keys.values()){
                if (key.keycode == keycode){
                    return true;
                }
            }
            return false;
        }

        public static Keys getKeyByCode(int keycode){
            for (Keys key : Keys.values()){
                if (key.keycode == keycode){
                    return key;
                }
            }
            return null;
        }
    }

    private void initializeKeyMap() {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.FORWARD, false);
        keys.put(Keys.BACKWARD, false);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Keys.contains(keycode)){
            updateKey(keycode, true);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (Keys.contains(keycode)){
            updateKey(keycode, false);
        }
        if(keycode == Input.Keys.TAB){
            DebugMode.toggleDebugMode();
        }
        if(keycode == Input.Keys.Q){
            AudioPlayer.toggleMusic();
        }
        return true;
    }

    private void updateKey(int keycode, boolean state){
        keys.put(Keys.getKeyByCode(keycode), state);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        setDirectionVector(screenX, screenY);
        return true;
    }

    public void setDirectionVector(float x, float y){
        directionVector = new Vector2(x, y);
    }

    public Vector2 getNormalisedDirection(){
        return CoordinateSystem.translateMousePosToWorldPosition(directionVector).nor();
    }

    public void processInput() {
        MovementVector movementVector = new MovementVector();

        if (keys.get(Keys.FORWARD)) {
            movementVector.forwardMovement(getNormalisedDirection());
        }
        else if (keys.get(Keys.BACKWARD)) {
            movementVector.backwardMovement(getNormalisedDirection());
        }

        if (keys.get(Keys.LEFT)) {
            movementVector.leftMovement(getNormalisedDirection());
        } else if (keys.get(Keys.RIGHT)) {
            movementVector.rightMovement(getNormalisedDirection());
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
