package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.*;
import com.genericgames.samurai.GameState;
import com.genericgames.samurai.IconFactory;
import com.genericgames.samurai.audio.AudioPlayer;
import com.genericgames.samurai.input.PlayerController;
import com.genericgames.samurai.io.GameIO;
import com.genericgames.samurai.model.*;
import com.genericgames.samurai.model.movable.character.ai.NPC;
import com.genericgames.samurai.physics.PhysicalWorldHelper;

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
        GL20 gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
            GameState state = WorldRenderer.getRenderer().getState();
            switch (state){
                case PAUSED :
                    WorldRenderer.getRenderer().setView(new PauseView(width, height));
                    break;
            }
//        renderer.setSize(width, height);
    }

    @Override
    public void show() {
        AudioPlayer.loadMusic("audio/music/soundtrack.mp3", true);
        AudioPlayer.playMusic();

        controller = new PlayerController(samuraiWorld);
        setPlayerController();

        renderer = WorldRenderer.getRenderer();
        renderer.setSamuraiWorld(samuraiWorld);
        renderer.setGameScreen(this);
        renderer.inGame();
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
        AudioPlayer.pauseMusic();
    }

    @Override
    public void beginContact(Contact contact) {
        Door door = getDoor(contact);
        if (door != null){
            samuraiWorld.setCurrentLevel(WorldFactory.createLevelWithSpawnPos(door.getFileName(),
                    Float.floatToRawIntBits(samuraiWorld.getPlayerCharacter().getX()), Float.floatToRawIntBits(samuraiWorld.getPlayerCharacter().getY()), door.getSpawnNumber()));
            samuraiWorld.getPhysicalWorld().setContactListener(this);
            renderer.setTiledMap(samuraiWorld.getCurrentLevelFile());
        } else if(PhysicalWorldHelper.isConversation(contact)){
            conversationCollision(contact);
        } else if(PhysicalWorldHelper.isBetweenPlayerAndEnemyAwarenessField(contact)){
            PhysicalWorldHelper.getEnemy(contact).setPlayerIsInAwarenessField(true);
        } else if(PhysicalWorldHelper.isBetweenArrowAndPhysicalObject(contact)){
            PhysicalWorldHelper.handleArrowCollision(contact);
        } else if(PhysicalWorldHelper.isCheckpoint(contact)){
            GameIO.saveGame(samuraiWorld.getCurrentLevel(), "AutoSave");
            PhysicalWorldHelper.flagCheckpointForDeletion(contact);
        } else if(PhysicalWorldHelper.isBetweenPlayerAndQuiver(contact)){
            PhysicalWorldHelper.handleQuiverCollision(contact, samuraiWorld);
        }
    }

    /**
     *
     * @param contact
     * @return the door in contact or null if the contact is not a door.
     */
    private Door getDoor(Contact contact){
        if (contact.getFixtureA().getBody().getUserData() instanceof Door
                && PhysicalWorldHelper.isPlayerBodyFixture(contact.getFixtureB())){
            return (Door)contact.getFixtureA().getBody().getUserData();
        } else if(contact.getFixtureB().getBody().getUserData() instanceof Door
                && PhysicalWorldHelper.isPlayerBodyFixture(contact.getFixtureA())){
            return (Door)contact.getFixtureB().getBody().getUserData();
        }
        return null;
    }

    private void conversationCollision(Contact contact){
        NPC npc = getNPC(contact);
        if(npc != null){
            DialogueIcon icon = IconFactory.createConversationIcon(npc.getX(), npc.getY(), npc.getDialogue());
            if(renderer.getView() instanceof GameView){
                ((GameView) renderer.getView()).setConversationIcon(icon);
            }
        }

    }

    public NPC getNPC(Contact contact){
        NPC npc = null;
        Object fixtureABody = contact.getFixtureA().getBody().getUserData();
        Object fixtureBBody = contact.getFixtureB().getBody().getUserData();
        if(fixtureABody instanceof NPC && fixtureABody.getClass() == NPC.class){
            npc = (NPC)contact.getFixtureA().getBody().getUserData();
        } else if(fixtureBBody instanceof NPC && fixtureBBody.getClass() == NPC.class){
            npc =(NPC)contact.getFixtureB().getBody().getUserData();
        }
        return npc;
    }

    @Override
    public void endContact(Contact contact) {
        if(contact.getFixtureA() != null && contact.getFixtureB() != null) {
            if (PhysicalWorldHelper.isConversation(contact)) {
                if (renderer.getView() instanceof GameView) {
                    ((GameView) renderer.getView()).setConversationIcon(null);
                }
            } else if(PhysicalWorldHelper.isBetweenPlayerAndEnemyAwarenessField(contact)){
                PhysicalWorldHelper.getEnemy(contact).setPlayerIsInAwarenessField(false);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
