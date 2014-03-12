package com.genericgames.samurai;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class TestInputProcessor extends InputAdapter {

    private TestRenderScreen screen;

    public TestInputProcessor(TestRenderScreen screen){
        this.screen = screen;
    }

    public boolean keyDown (int keycode) {
        if(Input.Keys.LEFT == keycode){
            screen.prevDialogue();
        } else if(Input.Keys.RIGHT == keycode){
            screen.nextDialogue();
        }
        return false;
    }
}
