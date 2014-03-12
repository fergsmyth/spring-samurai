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

public class DialogueManager implements IDialogueManager {

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Dialogue dialogue;
    private BitmapFont font;
    private Sprite rightIcon;
    private TweenManager tweenManager;
    private int index;

    @Override
    public void initialiseDialogue(String dialogue){
        rightIcon = new Sprite(new Texture(Gdx.files.internal("resources/icon/right-small.png")));
        Tween.registerAccessor(Sprite.class, new SpriteTween());
        this.tweenManager = new TweenManager();
        setupRightIconTween().start(tweenManager);
        this.dialogue = DialogueLoader.loader().loadDialogue(dialogue);
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        index = 0;
    }

    private Tween setupRightIconTween() {
        return Tween.to(rightIcon, SpriteTween.SIZE, 0.4f).target(0.9f).ease(TweenEquations.easeInQuad).repeatYoyo(4,0);
    }

    @Override
    public void renderPhrase() {
        drawBackground();
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
        Phrase currentPhrase = dialogue.getPhrases().get(index);
        Texture icon = new Texture(Gdx.files.internal("resources/icon/" + currentPhrase.getCharacter() + ".png"));

        batch.begin();
        batch.draw(icon, getLeftCornerX() + icon.getWidth() / 2, getLeftCornerY() + icon.getHeight() / 7, icon.getWidth(), icon.getHeight());
        font.setScale(1f);
        font.draw(batch, currentPhrase.getPhrase(), getLeftCornerX() + icon.getWidth() * 2, getLeftCornerY() + icon.getHeight());
        rightIcon.setPosition(getRightCornerX() - (rightIcon.getWidth() * 1.1f), getLeftCornerY() + rightIcon.getHeight() / 5);
        rightIcon.draw(batch);
        batch.end();
        tweenManager.update(Gdx.graphics.getDeltaTime());
    }

    private void drawBackground(){
        drawBox();
        drawBorder();
    }

    private void drawBorder() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(getLeftCornerX(), getLeftCornerY(), getBoxWidth(), getBoxHeight());
        shapeRenderer.end();
    }

    private void drawBox() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(getLeftCornerX(), getLeftCornerY(), getBoxWidth(), getBoxHeight());
        shapeRenderer.end();
    }

    private int getBoxHeight() {
        return Gdx.graphics.getHeight() / 3;
    }

    private int getBoxWidth() {
        return Gdx.graphics.getWidth() / 2;
    }

    private int getRightCornerX(){
        return getLeftCornerX() + getBoxWidth();
    }

    private int getLeftCornerY() {
        return Gdx.graphics.getHeight() / 3;
    }

    private int getLeftCornerX() {
        return Gdx.graphics.getWidth() / 4;
    }
}
