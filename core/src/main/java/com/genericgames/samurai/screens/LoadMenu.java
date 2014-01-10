package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.genericgames.samurai.menu.Menu;
import com.genericgames.samurai.io.Resource;

public class LoadMenu implements Screen {

    private SpriteBatch spriteBatch;
    private ScreenManager manager;
    private BitmapFont whiteFont;
    private Sprite background;
    private Sprite logo;
    private Stage stage;

    public LoadMenu(ScreenManager manager){
        this.manager = manager;
    }

    private EventListener backAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent && ((InputEvent)event).getType() == InputEvent.Type.touchDown){
                    manager.setMainMenu();
                    return true;
                }
                return false;
            }
        };
    }

    @Override
    public void render(float delta) {
        GLCommon gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        spriteBatch.begin();
        drawBackground();
        drawLogo();
        spriteBatch.end();
        stage.draw();
    }

    private void drawBackground() {
        spriteBatch.draw(background, 0, 0);
    }

    private void drawLogo() {
        spriteBatch.draw(logo, 0, Gdx.graphics.getHeight() - (logo.getHeight() + 10));
    }

    @Override
    public void resize(int width, int height) {
        stage = Menu.createLoadMenu(width, height, backAction());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        whiteFont = Resource.getHeaderFont();
        loadSpriteTextures();
    }

    private void loadSpriteTextures() {
        background = Resource.getSplashImage("background.png");
        background.setColor(1, 1, 1, 0);

        logo = Resource.getLogo("SpringSamuraiLogo.png");
        logo.setPosition(0, Gdx.graphics.getHeight() - (logo.getHeight() + 10));
        logo.setColor(1,1,1,0);
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
        spriteBatch.dispose();
        whiteFont.dispose();
        stage.dispose();
    }
}
