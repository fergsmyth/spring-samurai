package com.genericgames.samurai.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.genericgames.samurai.audio.AudioPlayer;


public class SplashScreen implements Screen{

    private SpriteBatch spriteBatch;
    private Sprite background;
    private Sprite foreground;
    private Sprite logo;
    private ScreenManager manager;
    private float deltaCount;
    private boolean soundPlayed;


    public SplashScreen(ScreenManager manager){
        this.manager = manager;
    }

    @Override
    public void render(float delta) {
        deltaCount += delta/2;
        GLCommon gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        playSound();
        drawBackground();
        drawForeground();
        drawLogo();
        spriteBatch.end();

        if (soundPlayed && AudioPlayer.finishedPlaying()){
            manager.setGameScreen();
        }
    }

    private void playSound() {
        if(deltaCount > 1 && !soundPlayed){
            AudioPlayer.playMusic();
            soundPlayed = true;
        }
    }

    private void drawLogo() {
        if (soundPlayed){
            spriteBatch.draw(logo, 0, 250);
        }
    }

    private void drawForeground() {
        if (!soundPlayed){
            foreground.setColor(1f, 1f, 1f, deltaCount);
        }
        foreground.setPosition(-10, -55);
        foreground.draw(spriteBatch);
    }

    private void drawBackground() {
        spriteBatch.draw(background, 0, 0);
    }

    @Override
    public void resize(int width, int height) {
//        Gdx.graphics.setDisplayMode(background.getWidth(), background.getHeight(), false);
    }

    @Override
    public void show() {
        AudioPlayer.loadMusic("sound/gong3.WAV", false);
        spriteBatch = new SpriteBatch();
        setupSprites();
    }

    private void setupSprites() {
        background = new Sprite(new Texture(Gdx.files.internal("splashscreen.png")));
        foreground = new Sprite(new Texture(Gdx.files.internal("foreground.png")));
        logo = new Sprite(new Texture(Gdx.files.internal("logo.png")));

        foreground.setColor(1, 1, 1, 0);
    }

    @Override
    public void hide() {
       dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}