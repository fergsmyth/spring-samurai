package com.genericgames.samurai;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class DialogueLoaderTest {

    private LwjglApplication application;
    private DialogueLoader loader;

    @Before
    public void setUp() throws Exception {
        application = GameTestingAsset.getTestApplicaton();
        loader = new DialogueLoader();
    }

    @Test
    public void testLoader(){
        Dialogue dialogues = loader.loadDialogue();
        assertNotEmpty(dialogues.getPhrases());
    }

    private void assertNotEmpty(Collection<String> phrases) {
        assertTrue(!phrases.isEmpty());
    }

}
