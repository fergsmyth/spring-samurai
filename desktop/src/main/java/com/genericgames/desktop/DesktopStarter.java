package com.genericgames.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.genericgames.samurai.SpringSamuraiGame;

public class DesktopStarter {

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Spring Samurai : Tale of Cokatoma";
        cfg.width = 1440;
        cfg.height = 805;
        SpringSamuraiGame game = new SpringSamuraiGame();
        new LwjglApplication(game, cfg);
    }
}