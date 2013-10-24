package com.example.mylibgdxgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.example.mylibgdxgame.model.MyWorld;
import com.example.mylibgdxgame.controller.WorldController;
import com.example.mylibgdxgame.view.WorldRenderer;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 28/09/13
 * Time: 16:14
 * To change this template use File | Settings | File Templates.
 */
public class GameScreen implements Screen, InputProcessor {

    private WorldController controller;
    private WorldRenderer renderer;
    private MyWorld myWorld;

    private int width, height;

    @Override
    public void render(float delta) {
        controller.update();

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
        myWorld = new MyWorld();
        renderer = new WorldRenderer(myWorld);
        controller = new WorldController(myWorld);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A){
            controller.leftPressed();
        }
        if (keycode == Input.Keys.D){
            controller.rightPressed();
        }
        if (keycode == Input.Keys.W){
            controller.upPressed();
        }
        if (keycode == Input.Keys.S){
            controller.downPressed();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A){
            controller.leftReleased();
        }
        if (keycode == Input.Keys.D){
            controller.rightReleased();
        }
        if (keycode == Input.Keys.W){
            controller.upReleased();
        }
        if (keycode == Input.Keys.S){
            controller.downReleased();
        }
        return true;    }

    @Override
    public boolean keyTyped(char character) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if (x < width / 2 && y > height / 2) {
            controller.leftPressed();
        }
        if (x > width / 2 && y > height / 2) {
            controller.rightPressed();
        }
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if (x < width / 2 && y > height / 2) {
            controller.leftReleased();
        }
        if (x > width / 2 && y > height / 2) {
            controller.rightReleased();
        }
        return true;    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean scrolled(int amount) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public MyWorld getMyWorld(){
        return myWorld;
    }
}
