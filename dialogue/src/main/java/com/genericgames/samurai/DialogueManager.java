package com.genericgames.samurai;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.genericgames.samurai.tween.SpriteTween;
import com.genericgames.samurai.view.DialogueView;
import com.genericgames.samurai.view.OverlayDialogueView;
import com.genericgames.samurai.view.PopUpDialogueView;

public class DialogueManager implements IDialogueManager {

    private Dialogue dialogue;
    private DialogueView view;
    private int index;

    @Override
    public void initialiseDialogue(String dialogue){
        this.dialogue = DialogueLoader.loader().loadDialogue(dialogue);
        Tween.registerAccessor(Sprite.class, new SpriteTween());
        view = new PopUpDialogueView();
        index = 0;

    }

    @Override
    public void renderPhrase() {
        drawPhrase();
    }

    @Override
    public void previousPhrase(){
        if(index != 0){
            --index;

        }
    }

    @Override
    public void nextPhrase() {
        if(hasNextPhrase()){
            ++index;
        }
    }

    private boolean hasNextPhrase() {
        return index < dialogue.getPhrases().size() - 1;
    }

    @Override
    public boolean isFinished() {
        return !hasNextPhrase();
    }

    public boolean hasDialogue(){
        return dialogue != null && !isFinished();
    }

    private void drawPhrase() {
        view.draw(dialogue.getPhrases().get(index));
    }


}
