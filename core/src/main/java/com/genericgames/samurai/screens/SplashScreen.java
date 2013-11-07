package com.genericgames.samurai.screens;

import aurelienribon.tweenengine.*;
import aurelienribon.tweenengine.equations.Quad;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.genericgames.samurai.audio.AudioPlayer;
import com.genericgames.samurai.tween.SpriteTween;


public class SplashScreen implements TweenCallback, Screen {

    private ScreenManager screenManager;
    private TweenManager tweenManager;
    private SpriteBatch spriteBatch;
    private Sprite background;
    private Sprite foreground;
    private Sprite logo;

    private float deltaCount;
    private boolean soundPlayed;


    public SplashScreen(ScreenManager manager){
        this.screenManager = manager;
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
        tweenManager.update(delta);
    }

    private void playSound() {
        if(deltaCount > 1 && !soundPlayed){
            AudioPlayer.playMusic();
            soundPlayed = true;
        }
    }

    private void drawLogo() {
        if (soundPlayed){
            spriteBatch.draw(logo, 0, Gdx.graphics.getHeight() - (logo.getHeight() + 10));
        }
    }

    private void drawForeground() {
        foreground.setPosition(-230, -55);
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
        loadSpriteTextures();

        Tween.registerAccessor(Sprite.class, new SpriteTween());
        this.tweenManager = new TweenManager();
        Tween.to(foreground, SpriteTween.ALPHA, 2f).target(1).ease(TweenEquations.easeInQuad).repeatYoyo(1, 2f).start(tweenManager);
    }

    private void loadSpriteTextures() {
        background = new Sprite(new Texture(Gdx.files.internal("waterfall.jpg")));
        background.setColor(1, 1, 1, 0);

        foreground = new Sprite(new Texture(Gdx.files.internal("foregroundA.png")));
        foreground.setColor(1, 1, 1, 0);

        logo = new Sprite(new Texture(Gdx.files.internal("logoA.png")));
        logo.setColor(1,1,1,0);
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

    @Override
    public void onEvent(int i, BaseTween<?> baseTween) {
        screenManager.setGameScreen();
    }
}
