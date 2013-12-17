package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.physics.box2d.*;
import com.genericgames.samurai.audio.AudioPlayer;
import com.genericgames.samurai.controller.PlayerController;
import com.genericgames.samurai.model.*;
import com.genericgames.samurai.physics.PhysicWorld;
import com.genericgames.samurai.utility.WorldRenderer;

public class GameScreen implements Screen, ContactListener {

    private PlayerController controller;
    private WorldRenderer renderer;
    private SamuraiWorld samuraiWorld;

    public GameScreen(SamuraiWorld samuraiWorld){
        this.samuraiWorld = samuraiWorld;
        this.samuraiWorld.getPhysicalWorld().setContactListener(this);
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
        AudioPlayer.loadMusic("audio/music/soundtrack.mp3", true);
        AudioPlayer.playMusic();

        controller = new PlayerController(samuraiWorld);
        setPlayerController();

        renderer = WorldRenderer.getRenderer();
        renderer.defaultState();
        renderer.setSamuraiWorld(samuraiWorld);
        renderer.setGameScreen(this);
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
            samuraiWorld.setCurrentLevel(WorldFactory.createLevelWithSpawnPos(door.getFileName(),
                    samuraiWorld.getPlayerCharacter(), door.getSpawnNumber()));
            samuraiWorld.getPhysicalWorld().setContactListener(this);
            renderer.setTiledMap(samuraiWorld.getCurrentLevelFile());
        }
    }

    /**
     *
     * @param contact
     * @return the door in contact or null if the contact is not a door.
     */
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
