package com.genericgames.samurai;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.genericgames.samurai.screens.ScreenManager;
import com.genericgames.samurai.screens.SplashScreen;

public class SpringSamuraiGame extends Game {

    private FPSLogger fpsLogger;
    private ScreenManager screenManager;

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
