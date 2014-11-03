package com.genericgames.samurai.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.genericgames.samurai.audio.AudioPlayer;
import com.genericgames.samurai.screens.MainMenu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MainMenuController extends InputAdapter {

    MainMenu mainMenu;

    private Map<Inputs, Boolean> inputs = new HashMap<Inputs, Boolean>();
    private int frame = 0;
    private Map<Inputs, Integer> lastRecordedPress = new HashMap<Inputs, Integer>();

    public MainMenuController(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        initializeInputMap();
    }

    public enum Inputs {
        SKIP(new ArrayList<Integer>() {{
            add(Input.Keys.SPACE);
            add(Input.Keys.ENTER);
            add(Input.Buttons.LEFT);
        }});

        private Collection<Integer> inputCodes;

        private Inputs(Collection<Integer> inputCodes){
            this.inputCodes = inputCodes;
        }

        public static boolean contains(int inputCode){
            for (Inputs input : Inputs.values()){
                if (input.inputCodes.contains(inputCode)){
                    return true;
                }
            }
            return false;
        }

        public static Inputs getInputByCode(int inputCode){
            for (Inputs input : Inputs.values()){
                if (input.inputCodes.contains(inputCode)){
                    return input;
                }
            }
            return null;
        }
    }

    private void initializeInputMap() {
        inputs.put(Inputs.SKIP, false);
    }

    private void detectDoubleTap(Inputs input) {
        if(lastRecordedPress.containsKey(input) &&
                (frame-lastRecordedPress.get(input)<15)){
            handleDoubleTap(input);
        }
    }

    private void handleDoubleTap(Inputs input) {
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Inputs.contains(keycode)){
            Inputs input = Inputs.getInputByCode(keycode);
            detectDoubleTap(input);
            lastRecordedPress.put(input, frame);
            updateInputState(keycode, true);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.BACKSPACE){
            System.exit(0);
        }
        if(keycode == Input.Keys.Q){
            AudioPlayer.toggleMusic();
        }

        if (Inputs.contains(keycode)){
            updateInputState(keycode, false);
        }

        return true;
    }

    private void updateInputState(int inputCode, boolean state){
        inputs.put(Inputs.getInputByCode(inputCode), state);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return true;
    }

    public void processInput() {
        frame++;

        if (inputs.get(Inputs.SKIP)){
            mainMenu.enableMenu();
        }

        //TODO For testing:
        handleDebugInput();
    }

    private void handleDebugInput() {
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if (Inputs.contains(button)){
            updateInputState(button, true);
        }
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if (Inputs.contains(button)){
            updateInputState(button, false);
        }
        return true;
    }
}
