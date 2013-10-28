package com.example.mylibgdxgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.example.mylibgdxgame.audio.AudioPlayer;
import com.example.mylibgdxgame.controller.PlayerController;
import com.example.mylibgdxgame.model.MyWorld;
import com.example.mylibgdxgame.model.WorldCreator;
import com.example.mylibgdxgame.view.WorldRenderer;

public class GameScreen implements Screen {

    private PlayerController controller;
    private WorldRenderer renderer;
    private MyWorld myWorld;

    @Override
    public void render(float delta) {
        controller.processInput();

        GLCommon gl = Gdx.gl;
        //clear the screen with Black
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        renderer.setSize(width, height);
    }

    @Override
    public void show() {
        myWorld = new MyWorld();
        WorldCreator.createPhysicalWorld(myWorld);
        renderer = new WorldRenderer(myWorld);
        controller = new PlayerController(myWorld);
        Gdx.input.setInputProcessor(controller);
        AudioPlayer.playMusic();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
        AudioPlayer.pauseMusic();
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }

}
