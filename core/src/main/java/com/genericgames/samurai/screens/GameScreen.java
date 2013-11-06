package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.genericgames.samurai.audio.AudioPlayer;
import com.genericgames.samurai.controller.PlayerController;
import com.genericgames.samurai.model.MyWorld;
import com.genericgames.samurai.model.WorldCreator;
import com.genericgames.samurai.utility.WorldRenderer;

public class GameScreen implements Screen {

    private PlayerController controller;
    private WorldRenderer renderer;
    private ScreenManager manager;
    private MyWorld myWorld;

    public GameScreen(ScreenManager manager){
        this.manager = manager;
    }

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
        AudioPlayer.loadMusic("music/KotoMusic.mp3", true);
        WorldCreator.createPhysicalWorld(myWorld);
        renderer = new WorldRenderer(myWorld);
        controller = new PlayerController(myWorld);
        Gdx.input.setInputProcessor(controller);
        AudioPlayer.playMusic();
    }

    @Override
    public void hide() {
        dispose();
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
