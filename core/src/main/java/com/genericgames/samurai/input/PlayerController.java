package com.genericgames.samurai.input;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.audio.AudioPlayer;
import com.genericgames.samurai.combat.AttackHelper;
import com.genericgames.samurai.combat.CombatHelper;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.PlayerCharacter;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.CoordinateSystem;
import com.genericgames.samurai.utility.DebugMode;
import com.genericgames.samurai.utility.MovementVector;
import com.genericgames.samurai.screens.WorldRenderer;

public class PlayerController extends InputAdapter {

    private SamuraiWorld samuraiWorld;
    private Vector2 directionVector = new Vector2();
    private Map<Inputs, Boolean> inputs = new HashMap<Inputs, Boolean>();

    public PlayerController(SamuraiWorld samuraiWorld) {
        this.samuraiWorld = samuraiWorld;
        initializeKeyMap();
    }

    public enum Inputs {
        LEFT(Input.Keys.A), RIGHT(Input.Keys.D), FORWARD(Input.Keys.W), BACKWARD(Input.Keys.S),
        ATTACK(Input.Buttons.LEFT), BLOCK(Input.Buttons.RIGHT), DODGE(Input.Buttons.MIDDLE);
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
        inputs.put(Inputs.ATTACK, false);
        inputs.put(Inputs.BLOCK, false);
        inputs.put(Inputs.DODGE, false);
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
        if(keycode == Input.Keys.ESCAPE){
            WorldRenderer.getRenderer().pause();
        }
        if(keycode == Input.Keys.I){
            WorldRenderer.getRenderer().inventory();
        }
        if(keycode == Input.Keys.Q){
            AudioPlayer.toggleMusic();
        }
        if(keycode == Input.Keys.C){
            WorldRenderer.getRenderer().dialogue();
        }
        if (keycode == Input.Keys.RIGHT){
            WorldRenderer.getRenderer().nextPhrase();
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
        PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
        Vector2 vector = movementVector.getScaledMovementVector(playerCharacter.getSpeed());

        State playerCharacterState = playerCharacter.getState();
        if(playerCharacterState.isAttacking()){
            vector = AttackHelper.getAttackMovementVector(playerCharacter, movementVector);
            CombatHelper.continueAttack(playerCharacter, samuraiWorld.getPhysicalWorld());
        }
        else if(playerCharacterState.isCharging()){
            CombatHelper.continueCharge(State.HEAVY_ATTACKING, playerCharacter);
        }
        else if(playerCharacterState.isDodging()){
            vector = CombatHelper.getDodgeVector();
            CombatHelper.continueDodge(playerCharacter);
        }

        PhysicalWorldHelper.movePlayer(samuraiWorld, directionVector, vector);
    }

    private void handleAttackInput(int button) {
        MovementVector movementVector =
                new MovementVector(CoordinateSystem.translateMouseToLocalPosition(directionVector));
        PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();

        State playerCharacterState = playerCharacter.getState();
        if(playerCharacterState.isAttackCapable()){
            if(button == Inputs.ATTACK.keycode){
                if(playerCharacterState.equals(State.CHARGING)){
                    CombatHelper.initiateAttack(State.LIGHT_ATTACKING, playerCharacter);
                }
                else if(playerCharacterState.equals(State.CHARGED)){
                    CombatHelper.initiateAttack(State.HEAVY_ATTACKING, playerCharacter);
                }
            }
        }

        PhysicalWorldHelper.movePlayer(samuraiWorld, directionVector,
                movementVector.getScaledMovementVector(playerCharacter.getSpeed()));
    }

    private void handleChargeAttackInput(int button) {
        MovementVector movementVector =
                new MovementVector(CoordinateSystem.translateMouseToLocalPosition(directionVector));
        PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();

        if(playerCharacter.getState().isAttackCapable()){
            if(button == Inputs.ATTACK.keycode){
                movementVector.stop();
                CombatHelper.initiateCharge(playerCharacter);
            }
        }

        PhysicalWorldHelper.movePlayer(samuraiWorld, directionVector,
                movementVector.getScaledMovementVector(playerCharacter.getSpeed()));
    }

    private void handleBlockInitiation(int button) {
        MovementVector movementVector =
                new MovementVector(CoordinateSystem.translateMouseToLocalPosition(directionVector));
        PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();

        if(playerCharacter.getState().isBlockCapable()){
            if(button == Inputs.BLOCK.keycode){
                movementVector.stop();
                CombatHelper.initiateBlock(playerCharacter);
            }
        }

        PhysicalWorldHelper.movePlayer(samuraiWorld, directionVector,
                movementVector.getScaledMovementVector(playerCharacter.getSpeed()));
    }

    private void handleBlockDiscontinuation(int button) {
        PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
        if(button == Inputs.BLOCK.keycode){
            CombatHelper.stopBlock(playerCharacter);
        }
    }

    private MovementVector handleMovementInput() {
        MovementVector movementVector =
                new MovementVector(CoordinateSystem.translateMouseToLocalPosition(directionVector));
        PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
        if(playerCharacter.getState().isMoveCapable()){

            if (inputs.get(Inputs.FORWARD)) {
                movementVector.forwardMovement(playerCharacter.getSpeed());
            } else if (inputs.get(Inputs.BACKWARD)) {
                movementVector.backwardMovement(playerCharacter.getSpeed());
            }
            if (inputs.get(Inputs.LEFT)){
                movementVector.leftMovement(playerCharacter.getSpeed());
            } else if(inputs.get(Inputs.RIGHT)){
                movementVector.rightMovement(playerCharacter.getSpeed());
            }

            if(movementVector.hasMoved()){
                CombatHelper.setDodgeVector(movementVector.getDodgeVector(playerCharacter.getSpeed()));
                playerCharacter.setState(State.WALKING);
                playerCharacter.incrementStateTime();
            }
            else{
                playerCharacter.setState(State.IDLE);
            }
        }
        return movementVector;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        handleBlockInitiation(button);
        handleDodgeInitiation(button);
        handleChargeAttackInput(button);
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        handleBlockDiscontinuation(button);
        handleAttackInput(button);
        return true;
    }

    private void handleDodgeInitiation(int button) {
        MovementVector movementVector =
                new MovementVector(CoordinateSystem.translateMouseToLocalPosition(directionVector));
        PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();

        if(playerCharacter.getState().isDodgeCapable()){
            if(button == Inputs.DODGE.keycode){
                CombatHelper.initiateDodge(playerCharacter);
            }
        }

        PhysicalWorldHelper.movePlayer(samuraiWorld, directionVector,
                movementVector.getScaledMovementVector(playerCharacter.getSpeed()));
    }
}
