package com.genericgames.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.genericgames.samurai.SpringSamuraiGame;

public class DesktopStarter {

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Spring Samurai : Tale of Cokatoma";
        int initialScreenWidth = 1440;
        int initialScreenHeight = 805;
        cfg.width = initialScreenWidth;
        cfg.height = initialScreenHeight;
        SpringSamuraiGame game = new SpringSamuraiGame();
        new LwjglApplication(game, cfg);
    }
}