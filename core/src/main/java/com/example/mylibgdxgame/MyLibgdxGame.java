package com.example.mylibgdxgame;

import com.badlogic.gdx.Game;
import com.example.mylibgdxgame.screens.GameScreen;

public class MyLibgdxGame extends Game {

    /**
     * Runs only once, when the application is instantiated
     */
    @Override
    public void create() {
        setScreen(new GameScreen());
    }

    /**
     * The main loop, it gets fired up to 60 times a second.
     */
    @Override
    public void render() {
        super.render();
    }

    public void update (float dt) {

    }

    /**
     * Kills application! But it calls Pause first.
     */
    public void dispose() {
        super.dispose();
        // TODO Auto-generated method stub
    }

    /**
     * Pauses game
     */
    public void pause() {
        super.pause();
        // TODO Auto-generated method stub
    }

    /**
     * Gets notified when the user changes orientation of the screen, or some notification from the
     * OS pushed your game aside, like incoming calls.
     */
    public void resize(int arg0, int arg1) {
        super.resize(arg0, arg1);
        // TODO Auto-generated method stub
    }

    /**
     * comes back from Pause.
     */
    public void resume() {
        super.resume();
        // TODO Auto-generated method stub
    }
}
