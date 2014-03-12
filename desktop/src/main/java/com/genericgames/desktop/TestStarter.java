package com.genericgames.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.genericgames.samurai.TestRenderGame;

public class TestStarter {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Test Renderer";
        cfg.width = 1200;
        cfg.height = 800;
        TestRenderGame game = new TestRenderGame();
        new LwjglApplication(game, cfg);
    }
}
