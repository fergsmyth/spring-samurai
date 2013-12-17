package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.genericgames.samurai.io.LoadGameHelper;
import com.genericgames.samurai.model.Level;
import com.genericgames.samurai.model.WorldFactory;
import com.genericgames.samurai.utility.ResourceHelper;

public class MainMenu implements Screen{

    public static final int SCALE_FACTOR = 6;
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
        whiteFont = ResourceHelper.getHeaderFont();
        loadSpriteTextures();
    }

    private void addButtons() {
        createButton("New Game", getTOP(), newGameAction());
        createButton("Load Game", getMIDDLE(), loadAction());
        createButton("Exit Game", getBOTTOM(), quitAction());
    }

    private EventListener loadAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent && ((InputEvent)event).getType() == InputEvent.Type.touchDown){
                    Level level = LoadGameHelper.loadGame();
                    if(level != null){
                        manager.setGameScreen(WorldFactory.createSamuraiWorld(level.getLevelFile(), level.getPlayerCharacter().getX(), level.getPlayerCharacter().getY()));
                    }
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
                    manager.setGameScreen(WorldFactory.createSamuraiWorld("map/Level1.tmx"));
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
        background = ResourceHelper.getSplashImage("background.png");
        background.setColor(1, 1, 1, 0);

        logo = ResourceHelper.getLogo("SpringSamuraiLogo.png");
        logo.setPosition(0, Gdx.graphics.getHeight() - (logo.getHeight() + 10));
        logo.setColor(1,1,1,0);
    }

    private int getX(){
        return (Gdx.graphics.getWidth() / 2) - Gdx.graphics.getWidth() / SCALE_FACTOR;
    }

    private int getTOP(){
        return Gdx.graphics.getHeight() / 2 + (Gdx.graphics.getHeight() / SCALE_FACTOR);
    }

    private int getMIDDLE(){
        return Gdx.graphics.getHeight() / 2;
    }

    private int getBOTTOM(){
        return Gdx.graphics.getHeight() / 2 - (Gdx.graphics.getHeight() / SCALE_FACTOR);
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
