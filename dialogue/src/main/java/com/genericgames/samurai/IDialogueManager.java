package com.genericgames.samurai;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.genericgames.samurai.view.DialogueView;

public interface IDialogueManager {

    void initialiseDialogue(SpriteBatch batch, ShapeRenderer shapeRenderer, String dialogue);
    void renderPhrase();
    void previousPhrase();
    void nextPhrase();
    boolean isFinished();
}
