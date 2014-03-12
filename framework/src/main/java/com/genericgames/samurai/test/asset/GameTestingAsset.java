package com.genericgames.samurai.test.asset;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class GameTestingAsset {

    private static LwjglApplication testApplicaton;

    public static LwjglApplication getTestApplication(){
        if(testApplicaton == null){
            System.out.println("Creating app");
            testApplicaton = createTestApplication();
        }
        return testApplicaton;
    }

    private static LwjglApplication createTestApplication() {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Test";
        cfg.useGL30=true;
        cfg.width = 1200;
        cfg.height = 800;
        TestGame game = new TestGame();
        return new LwjglApplication(game, cfg);
    }

}
