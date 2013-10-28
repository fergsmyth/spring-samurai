package com.example.mylibgdxgame.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.example.mylibgdxgame.audio.AudioPlayer;
import com.example.mylibgdxgame.model.MyWorld;
import com.example.mylibgdxgame.model.movable.State;
import com.example.mylibgdxgame.physics.PhysicalWorld;
import com.example.mylibgdxgame.view.CoordinateSystem;
import com.example.mylibgdxgame.view.DebugMode;
import com.example.mylibgdxgame.view.MovementVector;

public class WorldController extends InputAdapter {

    private MyWorld myWorld;
    private Vector2 directionVector = new Vector2();
    private Map<Keys, Boolean> keys = new HashMap<Keys, Boolean>();

    public WorldController(MyWorld myWorld) {
        this.myWorld = myWorld;
        initializeKeyMap();
    }

    public enum Keys {
        LEFT(Input.Keys.A),
        RIGHT(Input.Keys.D),
        FORWARD(Input.Keys.W),
        BACKWARD(Input.Keys.S);

        private int keycode;
        private Keys(int keycode){
            this.keycode = keycode;
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
        if (keycode == Input.Keys.A){
            updateKey(Keys.LEFT, true);
        }
        if (keycode == Input.Keys.D){
            updateKey(Keys.RIGHT, true);
        }
        if (keycode == Input.Keys.W){
            updateKey(Keys.FORWARD, true);
        }
        if (keycode == Input.Keys.S){
            updateKey(Keys.BACKWARD, true);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A){
            updateKey(Keys.LEFT, false);
        }
        if (keycode == Input.Keys.D){
            updateKey(Keys.RIGHT, false);
        }
        if (keycode == Input.Keys.W){
            updateKey(Keys.FORWARD, false);
        }
        if (keycode == Input.Keys.S){
            updateKey(Keys.BACKWARD, false);
        }
        if(keycode == Input.Keys.TAB){
            DebugMode.toggleDebugMode();
        }
        if(keycode == Input.Keys.Q){
            AudioPlayer.toggleMusic();
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        setDirectionVector(screenX, screenY);
        return true;
    }

    private void updateKey(Keys key, boolean state){
        keys.put(key, state);
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
