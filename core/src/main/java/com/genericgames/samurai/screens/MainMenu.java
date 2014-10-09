package com.genericgames.samurai.screens;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.genericgames.samurai.audio.AudioPlayer;
import com.genericgames.samurai.menu.Menu;
import com.genericgames.samurai.model.WorldFactory;
import com.genericgames.samurai.io.Resource;
import com.genericgames.samurai.tween.SpriteTween;
import com.genericgames.samurai.tween.StageActorTween;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainMenu implements Screen {

    public static final int SCALE_FACTOR = 6;
    private ScreenManager manager;

    private SpriteBatch spriteBatch;
    private Sprite genericGamesLogo;
    private Sprite background;
    private Sprite logo;
    private TweenManager tweenManager;
    private Sprite foreground;

    private BitmapFont whiteFont;

    private Stage stage;
    private Skin skin;

    public MainMenu(ScreenManager manager){
        this.manager = manager;
    }

    @Override
    public void render(float delta) {
        GL20 gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        spriteBatch.begin();
        drawGenericGamesLogo();
        drawBackground();
        drawForeground();
        drawLogo();
        spriteBatch.end();
        stage.draw();
        tweenManager.update(delta);

        //Play music after everything is loaded into memory to avoid track hiccups or skipping
        if(AudioPlayer.finishedPlaying()){
            AudioPlayer.playMusic();
        }
    }

    private void drawLogo() {
        logo.setPosition(0, Gdx.graphics.getHeight() - (logo.getHeight() + 10));
        logo.draw(spriteBatch);
    }

    private void drawForeground() {
        foreground.setPosition(-230, -55);
        foreground.draw(spriteBatch);
    }

    private void drawBackground() {
        background.draw(spriteBatch);
    }

    private void drawGenericGamesLogo() {
        genericGamesLogo.setPosition((Gdx.graphics.getWidth()/2) - (genericGamesLogo.getWidth()/2), 0);
        genericGamesLogo.draw(spriteBatch);
    }

    @Override
    public void resize(int width, int height) {
        stage = Menu.createButtonMenu(width, height, getButtonInfo(), new Color(1, 1, 1, 0), true);
        Gdx.input.setInputProcessor(stage);

        Tween.registerAccessor(Stage.class, new StageActorTween());
        Tween.to(stage, StageActorTween.ALPHA, 4f).delay(15f).target(1f).ease(TweenEquations.easeInQuad)
                .setCallback(enableStageActors()).start(tweenManager);
    }

    @Override
    public void show() {
        AudioPlayer.loadMusic("audio/music/Spring Samurai Main Theme.WAV", false);

        spriteBatch = new SpriteBatch();
        skin = new Skin();
        whiteFont = Resource.getHeaderFont();
        loadSpriteTextures();

        Tween.registerAccessor(Sprite.class, new SpriteTween());
        this.tweenManager = new TweenManager();
        Tween.to(genericGamesLogo, SpriteTween.ALPHA, 2f).target(1f).ease(TweenEquations.easeInQuad).repeatYoyo(1, 1f).start(tweenManager);
        Tween.to(background, SpriteTween.ALPHA, 4f).delay(5f).target(1f).ease(TweenEquations.easeInQuad).start(tweenManager);
        Tween.to(foreground, SpriteTween.ALPHA, 4f).delay(9f).target(1f).ease(TweenEquations.easeInQuad).repeatYoyo(1, 2f).start(tweenManager);
        Tween.to(logo, SpriteTween.ALPHA, 4f).delay(15f).target(1f).ease(TweenEquations.easeInQuad).start(tweenManager);

    }

    private Map<String, EventListener> getButtonInfo() {
        Map<String, EventListener> buttons = new LinkedHashMap<String, EventListener>();
        buttons.put("Exit Game", quitAction());
        buttons.put("Scoreboard", scoreboardAction());
        buttons.put("Arena Mode", arenaModeAction());
        buttons.put("Load Game", loadAction());
        buttons.put("New Game", newGameAction());
        return buttons;
    }

    private EventListener loadAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeListener.ChangeEvent){
                    manager.setLoadMenu();
                    return true;
                }
                return false;
            }
        };
    }

    private EventListener arenaModeAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeListener.ChangeEvent){
                    manager.setArenaMode();
                    return true;
                }
                return false;
            }
        };
    }

    private EventListener scoreboardAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeListener.ChangeEvent){
                    manager.setScoreboardScreen();
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
                if (event instanceof ChangeListener.ChangeEvent){
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
                if (event instanceof ChangeListener.ChangeEvent){
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
        genericGamesLogo = Resource.getLogo("GenericGames.png");
        genericGamesLogo.setColor(1, 1, 1, 0);

        background = Resource.getSplashImage("background.png");
        background.setColor(1, 1, 1, 0);

        foreground = Resource.getSplashImage("foreground.png");
        foreground.setColor(1, 1, 1, 0);

        logo = Resource.getLogo("SpringSamuraiLogo.png");
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

    public TweenCallback enableStageActors(){
        TweenCallback tweenCallback = new TweenCallback() {
            @Override
            public void onEvent(int i, BaseTween<?> baseTween) {
                for(Actor actor : stage.getActors()){
                    if(actor instanceof Button){
                        ((Button)actor).setDisabled(false);
                    }
                }
            }
        };
        return tweenCallback;
    }
}
