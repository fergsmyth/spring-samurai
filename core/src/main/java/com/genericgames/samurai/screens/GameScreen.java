package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.physics.box2d.*;
import com.genericgames.samurai.audio.AudioPlayer;
import com.genericgames.samurai.controller.PlayerController;
import com.genericgames.samurai.model.Door;
import com.genericgames.samurai.model.Level;
import com.genericgames.samurai.model.MyWorld;
import com.genericgames.samurai.model.WorldCreator;
import com.genericgames.samurai.model.movable.living.playable.PlayerCharacter;
import com.genericgames.samurai.utility.WorldRenderer;

public class GameScreen implements Screen, ContactListener {

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
        PlayerCharacter playerCharacter = new PlayerCharacter();
        Level firstLevel = new Level("TileTest.tmx", playerCharacter);
        myWorld = new MyWorld(firstLevel);
        AudioPlayer.loadMusic("music/KotoMusic.mp3", true);
        myWorld.setPhysicalWorld(WorldCreator.createPhysicalWorld(firstLevel));
        myWorld.getPhysicalWorld().setContactListener(this);
        renderer = WorldRenderer.getRenderer();
        renderer.setMyWorld(myWorld);
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

    @Override
    public void beginContact(Contact contact) {
        Door door = getDoor(contact);
        if (door != null){
            Level level = new Level(door.getFileName(), myWorld.getPlayerCharacter());
            myWorld.setCurrentLevel(level);
            myWorld.setPhysicalWorld(WorldCreator.createPhysicalWorld(level));
            myWorld.getPhysicalWorld().setContactListener(this);
            renderer.setTiledMap(myWorld.getCurrentLevel());
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
