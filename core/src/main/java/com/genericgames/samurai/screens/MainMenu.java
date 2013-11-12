package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainMenu implements Screen{

    private ScreenManager manager;

    private SpriteBatch spriteBatch;
    private Sprite background;
    private Sprite logo;

    private BitmapFont whiteFont;

    private Stage stage;
    private Skin skin;


    public MainMenu(ScreenManager manager){
        this.manager = manager;
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

    private void drawLogo() {
        spriteBatch.draw(logo, 0, Gdx.graphics.getHeight() - (logo.getHeight() + 10));
    }

    private void drawBackground() {
        spriteBatch.draw(background, 0, 0);
    }

    @Override
    public void resize(int width, int height) {
        stage = new Stage(width, height, true);
        Gdx.input.setInputProcessor(stage);
        addButtons();
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        skin = new Skin();
//        stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        whiteFont = new BitmapFont(Gdx.files.internal("heading.fnt"), false);
        loadSpriteTextures();
    }

    private void addButtons() {
        createButton("New Game", getTOP(), newGameAction());
        createButton("Load Game", getMIDDLE(), placeholderAction());
        createButton("Exit Game", getBOTTOM(), quitAction());
    }

    private EventListener placeholderAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent && ((InputEvent)event).getType() == InputEvent.Type.touchDown){
                    Gdx.app.log("Loading", "Wow you loaded the game");
                    return true;
                }
                return false;
            }
        };
    }

    private EventListener quitAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent && ((InputEvent)event).getType() == InputEvent.Type.touchDown){
                    Gdx.app.exit();
                    return true;
                }
                return false;
            }
        };
    }

    private EventListener newGameAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent && ((InputEvent)event).getType() == InputEvent.Type.touchDown){
                    manager.setGameScreen();
                    return true;
                }
                return false;
            }
        };
    }

    private TextButton createButton(String buttonText, int position, EventListener listener) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = whiteFont;
        TextButton button = new TextButton(buttonText, style);
        button.setWidth(400);
        button.setHeight(100);
        button.setPosition(getX(), position);
        button.addListener(listener);
        stage.addActor(button);
        return button;
    }

    private void loadSpriteTextures() {
        background = new Sprite(new Texture(Gdx.files.internal("waterfall.jpg")));
        background.setColor(1, 1, 1, 0);

        logo = new Sprite(new Texture(Gdx.files.internal("logoA.png")));
        logo.setPosition(0, Gdx.graphics.getHeight() - (logo.getHeight() + 10));
        logo.setColor(1,1,1,0);
    }

    private int getX(){
        return (Gdx.graphics.getWidth() / 2) - 200;
    }

    private int getTOP(){
        return Gdx.graphics.getHeight() / 2 + 150;
    }

    private int getMIDDLE(){
        return Gdx.graphics.getHeight() / 2;
    }

    private int getBOTTOM(){
        return Gdx.graphics.getHeight() / 2 - 150;
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
        whiteFont.dispose();
        stage.dispose();
        skin.dispose();
    }
}
