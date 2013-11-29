package com.genericgames.samurai.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.audio.AudioPlayer;
import com.genericgames.samurai.combat.CombatHelper;
import com.genericgames.samurai.model.MyWorld;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.playable.PlayerCharacter;
import com.genericgames.samurai.physics.PhysicalWorld;
import com.genericgames.samurai.utility.DebugMode;
import com.genericgames.samurai.utility.MovementVector;

public class PlayerController extends InputAdapter {

    private MyWorld myWorld;
    private Vector2 directionVector = new Vector2();
    private Map<Inputs, Boolean> inputs = new HashMap<Inputs, Boolean>();

    public PlayerController(MyWorld myWorld) {
        this.myWorld = myWorld;
        initializeKeyMap();
    }

    public enum Inputs {
        LEFT(Input.Keys.A), RIGHT(Input.Keys.D), FORWARD(Input.Keys.W), BACKWARD(Input.Keys.S),
                LIGHT_ATTACK(Input.Buttons.LEFT);
//        LIGHT_ATTACK(Input.Keys.SPACE);
        private int keycode;
        private Inputs(int keycode){
            this.keycode = keycode;
        }

        public static boolean contains(int keycode){
            for (Inputs key : Inputs.values()){
                if (key.keycode == keycode){
                    return true;
                }
            }
            return false;
        }

        public static Inputs getKeyByCode(int keycode){
            for (Inputs key : Inputs.values()){
                if (key.keycode == keycode){
                    return key;
                }
            }
            return null;
        }
    }

    private void initializeKeyMap() {
        inputs.put(Inputs.LEFT, false);
        inputs.put(Inputs.RIGHT, false);
        inputs.put(Inputs.FORWARD, false);
        inputs.put(Inputs.BACKWARD, false);
        inputs.put(Inputs.LIGHT_ATTACK, false);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Inputs.contains(keycode)){
            updateKey(keycode, true);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (Inputs.contains(keycode)){
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
        inputs.put(Inputs.getKeyByCode(keycode), state);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        setMouseDirection(screenX, screenY);
        return true;
    }

    public void setMouseDirection(float x, float y){
        directionVector = new Vector2(x, y);
    }

    public void processInput() {
        MovementVector movementVector = handleMovementInput();
        PlayerCharacter playerCharacter = myWorld.getPlayerCharacter();

        if(playerCharacter.getState().isAttacking()){
            CombatHelper.continueAttack(State.LIGHT_ATTACKING, playerCharacter, myWorld.getPhysicalWorld());
        }

        PhysicalWorld.moveBody(myWorld.getPhysicalWorld(), playerCharacter, directionVector, movementVector.getMovementVector());
    }

    private void handleAttackInput(int button) {
        MovementVector movementVector = new MovementVector(directionVector);
        PlayerCharacter playerCharacter = myWorld.getPlayerCharacter();

        if(playerCharacter.getState().isAttackCapable()){
            if(button == Inputs.LIGHT_ATTACK.keycode){
                movementVector.stop();
                CombatHelper.initiateAttack(State.LIGHT_ATTACKING, playerCharacter);
            }
        }

        PhysicalWorld.moveBody(myWorld.getPhysicalWorld(), myWorld.getPlayerCharacter(), directionVector, movementVector.getMovementVector());
    }

    private MovementVector handleMovementInput() {
        MovementVector movementVector = new MovementVector(directionVector);
        if(myWorld.getPlayerCharacter().getState().isMoveCapable()){

            if (inputs.get(Inputs.FORWARD)) {
                movementVector.forwardMovement();
            } else if (inputs.get(Inputs.BACKWARD)) {
                movementVector.backwardMovement();
            }
            if (inputs.get(Inputs.LEFT)){
                movementVector.leftMovement();
            } else if(inputs.get(Inputs.RIGHT)){
                movementVector.rightMovement();
            }

            if(movementVector.hasMoved()){
                myWorld.getPlayerCharacter().setState(State.WALKING);
                myWorld.getPlayerCharacter().setStateTime(myWorld.getPlayerCharacter().getStateTime()+1);
            }
            else{
                myWorld.getPlayerCharacter().setState(State.IDLE);
            }
        }
        return movementVector;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        handleAttackInput(button);
        return true;
    }
}
