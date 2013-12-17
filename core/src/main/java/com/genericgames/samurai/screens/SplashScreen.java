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
import com.genericgames.samurai.utility.ResourceHelper;


public class SplashScreen implements Screen {

    private TweenManager tweenManager;
    private SpriteBatch spriteBatch;
    private Sprite background;
    private Sprite foreground;
    private Sprite logo;

    private float deltaCount;

    public SplashScreen(){

    }

    @Override
    public void render(float delta) {
        deltaCount += delta/2;
        GLCommon gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        drawBackground();
        drawForeground();
        drawLogo();
        spriteBatch.end();
        tweenManager.update(delta);
    }

    private void drawLogo() {
        spriteBatch.draw(logo, 0, Gdx.graphics.getHeight() - (logo.getHeight() + 10));
    }

    private void drawForeground() {
        foreground.setPosition(-230, -55);
        foreground.draw(spriteBatch);
    }

    private void drawBackground() {
        spriteBatch.draw(background, 0, 0);
    }

    @Override
    public void show() {
        AudioPlayer.loadMusic("audio/sound/gong.wav", false);
        spriteBatch = new SpriteBatch();
        loadSpriteTextures();

        Tween.registerAccessor(Sprite.class, new SpriteTween());
        this.tweenManager = new TweenManager();
        Tween.to(foreground, SpriteTween.ALPHA, 1.5f).target(0.7f).ease(TweenEquations.easeInQuad).repeatYoyo(1, 2f).setCallback(transitionScreen()).start(tweenManager);
        Tween.to(logo, SpriteTween.ALPHA, 0f).target(1).ease(TweenEquations.easeInQuad).delay(2f).setCallback(audioPlayCallback()).start(tweenManager);
    }


    private void loadSpriteTextures() {
        background = ResourceHelper.getSplashImage("background.png");
        background.setColor(1, 1, 1, 0);

        foreground = ResourceHelper.getSplashImage("foreground.png");
        foreground.setColor(1, 1, 1, 0);

        logo = ResourceHelper.getLogo("SpringSamuraiLogo.png");
        logo.setColor(1,1,1,0);
    }

    @Override
    public void resize(int width, int height) {

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

    public TweenCallback audioPlayCallback(){
        TweenCallback tweenCallback = new TweenCallback() {
            @Override
            public void onEvent(int i, BaseTween<?> baseTween) {
                AudioPlayer.playMusic();
            }
        };
        return tweenCallback;
    }

    public TweenCallback transitionScreen(){
        TweenCallback tweenCallback = new TweenCallback() {
            @Override
            public void onEvent(int i, BaseTween<?> baseTween) {
               Gdx.app.log("Changing :", "Main menu");
                ScreenManager.manager.setMainMenu();
            }
        };
        return tweenCallback;
    }
}
