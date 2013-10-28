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
            leftPressed();
        }
        if (keycode == Input.Keys.D){
            rightPressed();
        }
        if (keycode == Input.Keys.W){
            upPressed();
        }
        if (keycode == Input.Keys.S){
            downPressed();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A){
            leftReleased();
        }
        if (keycode == Input.Keys.D){
            rightReleased();
        }
        if (keycode == Input.Keys.W){
            upReleased();
        }
        if (keycode == Input.Keys.S){
            downReleased();
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

    public void leftPressed() {
        keys.put(Keys.LEFT, true);
    }

    public void rightPressed() {
        keys.put(Keys.RIGHT, true);
    }

    public void upPressed() {
        keys.put(Keys.FORWARD, true);
    }

    public void downPressed() {
        keys.put(Keys.BACKWARD, true);
    }

    public void leftReleased() {
        keys.put(Keys.LEFT, false);
    }

    public void rightReleased() {
        keys.put(Keys.RIGHT, false);
    }

    public void upReleased() {
        keys.put(Keys.FORWARD, false);
    }

    public void downReleased() {
        keys.put(Keys.BACKWARD, false);
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
