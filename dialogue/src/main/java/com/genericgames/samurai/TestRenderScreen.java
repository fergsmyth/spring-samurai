package com.genericgames.samurai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TestRenderScreen implements Screen {

    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private DialogueManager manager;
    private TmxMapLoader mapLoader;
    private SpriteBatch batch;
    private static final float CAMERA_HEIGHT = 20f;
    private static final float CAMERA_WIDTH = 20f;

    @Override
    public void render(float delta) {
        camera.position.set(10, 10, 0);
        camera.setToOrtho(false, 20, 20);
        camera.update();
        GL20 gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render();
        manager.renderPhrase();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load("map/Level1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/32f);
        camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        manager = new DialogueManager();
        manager.initialiseDialogue("dialogue.xml");
        Gdx.input.setInputProcessor(new TestInputProcessor(this));
    }

    public void nextDialogue(){
        manager.nextPhrase();
    }

    public void prevDialogue(){
        manager.previousPhrase();
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
