package com.genericgames.samurai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglGraphics;
import com.genericgames.samurai.test.asset.GameTestingAsset;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertTrue;

public class DialogueLoaderTest {

    private static LwjglApplication application = GameTestingAsset.getTestApplication();
    private DialogueLoader loader;
    private Dialogue dialogue;

    @Before
    public void setUp() throws Exception {
        loader = DialogueLoader.loader();
        dialogue = loader.loadDialogue("dialogue.xml");
    }

    //@Test
    public void testLoader(){
        assertNotEmpty(dialogue.getPhrases());
    }

    //@Test
    public void testPhraseLength(){
        Iterator<Phrase> iterator = dialogue.getPhrases().iterator();
        while(iterator.hasNext()){
            Phrase phrase = iterator.next();
            assertHasLength(phrase.getPhrase());
            assertHasLength(phrase.getCharacter());
        }
    }

    private void assertHasLength(String phrase){
        assertTrue(!phrase.isEmpty());
    }

    private void assertNotEmpty(Collection<?> phrases) {
        assertTrue(!phrases.isEmpty());
    }

}