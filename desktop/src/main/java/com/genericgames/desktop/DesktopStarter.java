package com.genericgames.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.genericgames.samurai.SpringSamuraiGame;

public class DesktopStarter {

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Cokatoma's Game";
        cfg.useGL20 = true;
        cfg.width = 1920;
        cfg.height = 1080;
        SpringSamuraiGame game = new SpringSamuraiGame();
        new LwjglApplication(game, cfg);
    }
}