package com.genericgames.samurai.test.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;

public class TestScreen implements Screen {

    @Override
    public void render(float v) {
        GL20 gl = Gdx.gl30;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int i, int i2) {}

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {}
}
