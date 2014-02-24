package com.genericgames.samurai;

import com.badlogic.gdx.Game;

public class TestRenderGame extends Game {

    @Override
    public void create() {
        this.setScreen(new TestRenderScreen());
    }
}
