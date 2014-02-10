package com.genericgames.samurai;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class GameTestingAsset {

    private static LwjglApplication testApplicaton;

    public static LwjglApplication getTestApplicaton(){
        if(testApplicaton == null){
            testApplicaton = createTestApplication();
        }
        return testApplicaton;
    }

    private static LwjglApplication createTestApplication() {
            LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
            TestGame game = new TestGame();
            return new LwjglApplication(game, cfg);
    }

}
