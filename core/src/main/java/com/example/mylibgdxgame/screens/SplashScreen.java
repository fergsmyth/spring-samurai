package com.example.mylibgdxgame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.mylibgdxgame.audio.AudioPlayer;


public class SplashScreen implements Screen{

    private SpriteBatch spriteBatch;
    private Texture splashBackground;
    private Texture frontBackground;
    private Texture logo;
    private Game myGame;
    private float deltaCount;
    private boolean soundPlayed;

    public SplashScreen(Game myGame){
        this.myGame = myGame;
    }

    @Override
    public void render(float delta) {
        deltaCount += delta;
        GLCommon gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        setAlpha(1);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(splashBackground, 0, 0);
        if (deltaCount > 0.5){
            setAlpha(deltaCount);
            spriteBatch.draw(frontBackground, 0, -50);
            if (deltaCount > 1){
                spriteBatch.draw(logo, 0, 250);
            }
        }
        spriteBatch.end();
        if(deltaCount > 1 && !soundPlayed){
            AudioPlayer.playGong();
            soundPlayed = true;
        }
        if (deltaCount > 3.4){
            myGame.setScreen(new GameScreen());
        }
    }

    private void setAlpha(float alpha) {
        Color color = spriteBatch.getColor();
        if (alpha > 1){
            alpha = 1;
        }
        spriteBatch.setColor(color.r, color.g, color.b, alpha);
    }


    @Override
    public void resize(int width, int height) {
//        Gdx.graphics.setDisplayMode(splashBackground.getWidth(), splashBackground.getHeight(), false);
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        splashBackground = new Texture(Gdx.files.internal("splashscreen.png"));
        frontBackground = new Texture(Gdx.files.internal("foreground.png"));
        logo = new Texture(Gdx.files.internal("logo.png"));
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
