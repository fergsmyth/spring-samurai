package com.genericgames.samurai.input;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.audio.AudioPlayer;
import com.genericgames.samurai.combat.AttackHelper;
import com.genericgames.samurai.combat.CombatHelper;
import com.genericgames.samurai.maths.MyMathUtils;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.PlayerCharacter;
import com.genericgames.samurai.physics.Arrow;
import com.genericgames.samurai.physics.PhysicalWorldFactory;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.CoordinateSystem;
import com.genericgames.samurai.utility.DebugMode;
import com.genericgames.samurai.utility.MovementVector;
import com.genericgames.samurai.screens.WorldRenderer;

public class PlayerController extends InputAdapter {

    private SamuraiWorld samuraiWorld;
    private Vector2 directionVector = new Vector2();
    private Map<Inputs, Boolean> inputs = new HashMap<Inputs, Boolean>();
    private int frame = 0;
    private Map<Inputs, Integer> lastRecordedPress = new HashMap<Inputs, Integer>();
    private boolean directionRelativeMovement = false;

    public PlayerController(SamuraiWorld samuraiWorld) {
        this.samuraiWorld = samuraiWorld;
        initializeKeyMap();
    }

    public enum Inputs {
        LEFT(Input.Keys.A), RIGHT(Input.Keys.D), UP(Input.Keys.W), DOWN(Input.Keys.S),
        ATTACK(Input.Buttons.LEFT), BLOCK(Input.Buttons.RIGHT), DODGE(Input.Buttons.MIDDLE), FIRE(Input.Keys.F),;
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

        public boolean isDirection() {
            return this.equals(LEFT) || this.equals(RIGHT) || this.equals(UP) || this.equals(DOWN);
        }
    }

    private void initializeKeyMap() {
        inputs.put(Inputs.LEFT, false);
        inputs.put(Inputs.RIGHT, false);
        inputs.put(Inputs.UP, false);
        inputs.put(Inputs.DOWN, false);
        inputs.put(Inputs.ATTACK, false);
        inputs.put(Inputs.BLOCK, false);
        inputs.put(Inputs.DODGE, false);
        inputs.put(Inputs.FIRE, false);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Inputs.contains(keycode)){
            Inputs input = Inputs.getKeyByCode(keycode);
            detectDoubleTap(input);
            lastRecordedPress.put(input, frame);
			updateKey(keycode, true);
        }
        return true;
    }

    private void detectDoubleTap(Inputs input) {
        if(lastRecordedPress.containsKey(input) &&
                (frame-lastRecordedPress.get(input)<15)){
            handleDoubleTap(input);
        }
    }

    private void handleDoubleTap(Inputs input) {
        if(input.isDirection()){
            handleDodgeInitiationByDoubleTapDirection();
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        //TODO Remove. For testing purposes:
        if(keycode == Input.Keys.BACKSPACE){
            System.exit(0);
        }
        if(keycode == Input.Keys.F12){
            switchControlScheme();
        }

        if (Inputs.contains(keycode)){
            updateKey(keycode, false);
        }
        if(keycode == Input.Keys.TAB){
            DebugMode.toggleDebugMode();
        }
        if(keycode == Input.Keys.Q){
            AudioPlayer.toggleMusic();
        }

        if(keycode == Input.Keys.ESCAPE){
            WorldRenderer.getRenderer().pause();
        }
        if(keycode == Input.Keys.I){
            WorldRenderer.getRenderer().inventory();
        }
        if(keycode == Input.Keys.C){
            WorldRenderer.getRenderer().dialogue();
        }
        if (keycode == Input.Keys.RIGHT){
            WorldRenderer.getRenderer().nextPhrase();
        }
        if (keycode == Input.Keys.F){
            PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
            Vector2 playerDir = MyMathUtils.getVectorFromPointAndAngle(playerCharacter.getX(), playerCharacter.getY(),
                    playerCharacter.getRotation());
            playerDir.y = -playerDir.y;
            Arrow arrow = PhysicalWorldFactory.createArrow(playerCharacter.getX(), playerCharacter.getY(),
                    playerDir, samuraiWorld.getPhysicalWorld());
            samuraiWorld.addArrow(arrow);
        }
        return true;
    }

    private void switchControlScheme() {
        directionRelativeMovement = !directionRelativeMovement;
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
        frame++;
        //Have to manually set mouse position here, because mouseMoved() is not called when a key is pressed.
        setMouseDirection(Gdx.input.getX(), Gdx.input.getY());
        PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
        if(!playerCharacter.getState().equals(State.DEAD)){
            MovementVector movementVector = handleMovementInput();
            playerCharacter.incrementStateTime();
            Vector2 vector = movementVector.getScaledMovementVector(playerCharacter.getSpeed());

            State playerCharacterState = playerCharacter.getState();
            if(playerCharacterState.isAttacking()){
                vector = AttackHelper.getAttackMovementVector(playerCharacter, movementVector);
                CombatHelper.continueAttack(playerCharacter, samuraiWorld);
            }
            else if(playerCharacterState.isCharging()){
                CombatHelper.continueCharge(State.HEAVY_ATTACKING, playerCharacter);
            }
            else if(playerCharacterState.isDodging()){
                vector = CombatHelper.getDodgeVector();
                CombatHelper.continueDodge(playerCharacter);
            }
            else if(playerCharacterState.isKnockedBack()){
                //TODO add movement to knockback?
//                vector = CombatHelper.getDodgeVector();
                CombatHelper.continueKnockBack(playerCharacter);
            }

            PhysicalWorldHelper.movePlayer(samuraiWorld, directionVector, vector);
        }
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

            getCorrespondingMovementVector(movementVector, playerCharacter);

            if(movementVector.hasMoved()){
                CombatHelper.setDodgeVector(movementVector.getDodgeVector(playerCharacter.getSpeed()));
                playerCharacter.setState(State.WALKING);
            }
            else{
                playerCharacter.setState(State.IDLE);
            }
        }
        return movementVector;
    }

    private void getCorrespondingMovementVector(MovementVector movementVector, PlayerCharacter playerCharacter) {
        if (inputs.get(Inputs.UP)) {
            if(directionRelativeMovement){
                movementVector.forwardMovement(playerCharacter.getSpeed());
            }
            else {
                movementVector.northMovement(playerCharacter.getSpeed());
            }
        } else if (inputs.get(Inputs.DOWN)) {
            if(directionRelativeMovement){
                movementVector.backwardMovement(playerCharacter.getSpeed());
            }
            else {
                movementVector.southMovement(playerCharacter.getSpeed());
            }
        }
        if (inputs.get(Inputs.LEFT)){
            if(directionRelativeMovement){
                movementVector.leftMovement(playerCharacter.getSpeed());
            }
            else {
                movementVector.westMovement(playerCharacter.getSpeed());
            }
        } else if(inputs.get(Inputs.RIGHT)){
            if(directionRelativeMovement){
                movementVector.rightMovement(playerCharacter.getSpeed());
            }
            else {
                movementVector.eastMovement(playerCharacter.getSpeed());
            }
        }
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
        if(!playerCharacter.getState().equals(State.DEAD)){
            handleBlockInitiation(button);
            handleDodgeInitiationByMouse(button);
            handleChargeAttackInput(button);
        }
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
        if(!playerCharacter.getState().equals(State.DEAD)){
            handleBlockDiscontinuation(button);
            handleAttackInput(button);
        }
        return true;
    }

    private void handleDodgeInitiationByMouse(int button) {
        if(button == Inputs.DODGE.keycode){
            MovementVector movementVector =
                    new MovementVector(CoordinateSystem.translateMouseToLocalPosition(directionVector));
            PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();

            //if player is not moving, dodge backwards by default:
            if(playerCharacter.getState().equals(State.IDLE)){
                movementVector.backwardMovement(playerCharacter.getSpeed());
                CombatHelper.setDodgeVector(movementVector.getDodgeVector(playerCharacter.getSpeed()));
            }

            initiateDodge(movementVector);
        }
    }

    private void handleDodgeInitiationByDoubleTapDirection() {
        PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
        MovementVector movementVector =
                new MovementVector(CoordinateSystem.translateMouseToLocalPosition(directionVector));
        getCorrespondingMovementVector(movementVector, playerCharacter);
        initiateDodge(movementVector);
    }

    private void initiateDodge(MovementVector movementVector) {
        PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
        if(playerCharacter.getState().isDodgeCapable()){
            CombatHelper.initiateDodge(playerCharacter);
        }

        PhysicalWorldHelper.movePlayer(samuraiWorld, directionVector,
                movementVector.getScaledMovementVector(playerCharacter.getSpeed()));
    }
}
