package com.genericgames.samurai;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.genericgames.samurai.screens.ScreenManager;

public class SpringSamuraiGame extends Game {

    public static int INITIAL_SCREEN_WIDTH;
    public static int INITIAL_SCREEN_HEIGHT;

    private FPSLogger fpsLogger;
    private ScreenManager screenManager;

    public SpringSamuraiGame(int initialScreenWidth, int initialScreenHeight) {
        super();
        INITIAL_SCREEN_WIDTH = initialScreenWidth;
        INITIAL_SCREEN_HEIGHT = initialScreenHeight;
    }

    @Override
    public void create() {
        fpsLogger = new FPSLogger();
        ScreenManager.initialiseScreenManager(this);
    }

    @Override
    public void render() {
        super.render();
        fpsLogger.log();
    }

    public void update (float dt) {
    }

    public void dispose() {
        super.dispose();
    }

    public void pause() {
        super.pause();
    }

    public void resize(int arg0, int arg1) {
        super.resize(arg0, arg1);
    }

    public void resume() {
        super.resume();
    }
}
