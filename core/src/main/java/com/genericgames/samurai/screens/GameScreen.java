package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.genericgames.samurai.audio.AudioPlayer;
import com.genericgames.samurai.controller.PlayerController;
import com.genericgames.samurai.model.*;
import com.genericgames.samurai.model.movable.living.playable.PlayerCharacter;
import com.genericgames.samurai.utility.ResourceHelper;
import com.genericgames.samurai.utility.WorldRenderer;

public class GameScreen implements Screen, ContactListener {

    private PlayerController controller;
    private WorldRenderer renderer;
    private ScreenManager manager;
    private SamuraiWorld samuraiWorld;

    public GameScreen(ScreenManager manager){
        this.manager = manager;
    }

    @Override
    public void render(float delta) {
        controller.processInput();
        GLCommon gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        renderer.setSize(width, height);
    }

    @Override
    public void show() {
        PlayerCharacter playerCharacter = new PlayerCharacter();
        Level firstLevel = new Level("Level1.tmx", playerCharacter);
        samuraiWorld = new SamuraiWorld(firstLevel);
        AudioPlayer.loadMusic("audio/music/soundtrack.mp3", true);
        samuraiWorld.setPhysicalWorld(WorldObjectFactory.createPhysicalWorld(firstLevel));
        samuraiWorld.getPhysicalWorld().setContactListener(this);
        renderer = WorldRenderer.getRenderer();
        renderer.defaultState();
        renderer.setSamuraiWorld(samuraiWorld);
        renderer.setGameScreen(this);
        controller = new PlayerController(samuraiWorld);
        setPlayerController();
        AudioPlayer.playMusic();
    }

    public void setPlayerController() {
        Gdx.input.setInputProcessor(controller);
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

    @Override
    public void beginContact(Contact contact) {
        Door door = getDoor(contact);
        if (door != null){
            Level level = new Level(door.getFileName(), samuraiWorld.getPlayerCharacter());
            samuraiWorld.setCurrentLevel(level);
            SpawnPoint point = samuraiWorld.getSpawnPointByPosition(door.getSpawnNumber());
            samuraiWorld.getPlayerCharacter().setPosition(point.getPositionX(), point.getPositionY());
            samuraiWorld.setPhysicalWorld(WorldObjectFactory.createPhysicalWorld(level));
            samuraiWorld.getPhysicalWorld().setContactListener(this);
            renderer.setTiledMap(samuraiWorld.getCurrentLevel());
        }
    }

    private Door getDoor(Contact contact){
        if (contact.getFixtureA().getBody().getUserData() instanceof Door){
            return (Door)contact.getFixtureA().getBody().getUserData();
        } else if(contact.getFixtureB().getBody().getUserData() instanceof Door){
            return (Door)contact.getFixtureB().getBody().getUserData();
        }
        return null;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
