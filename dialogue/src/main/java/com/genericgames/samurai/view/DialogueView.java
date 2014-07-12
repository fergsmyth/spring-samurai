package com.genericgames.samurai.view;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.genericgames.samurai.Phrase;
import com.genericgames.samurai.tween.SpriteTween;

public abstract class DialogueView {

    protected ShapeRenderer shapeRenderer;
    protected TweenManager tweenManager;
    protected Texture speakerIcon;
    protected Sprite nextPhrase;
    protected SpriteBatch batch;
    protected BitmapFont font;

    public DialogueView(SpriteBatch batch, ShapeRenderer shapeRenderer, BitmapFont font) {
        this.font = font;
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;
        nextPhrase = new Sprite(new Texture(Gdx.files.internal("resources/icon/right-small.png")));
        tweenManager = new TweenManager();
        setupRightIconTween().start(tweenManager);
    }

    public void draw(Phrase currentPhrase){
        drawBackground();
        speakerIcon = new Texture(Gdx.files.internal("resources/icon/" + currentPhrase.getCharacter() + ".png"));
        batch.begin();
        batch.draw(speakerIcon, getLeftCornerX() - (speakerIcon.getWidth() / 4), getLeftCornerY() + speakerIcon.getHeight() / 7, speakerIcon.getWidth(), speakerIcon.getHeight());
        font.setScale(1f);
        font.draw(batch, currentPhrase.getPhrase(), textPosX(), textPosY());
        nextPhrase.draw(batch);
        batch.end();
        tweenManager.update(Gdx.graphics.getDeltaTime());
    }

    private Tween setupRightIconTween() {
        nextPhrase.setPosition(getRightCornerX() - (nextPhrase.getWidth() * 1.1f), getLeftCornerY() + nextPhrase.getHeight() / 5);
        return Tween.to(nextPhrase, SpriteTween.SIZE, 0.4f).target(0.9f).ease(TweenEquations.easeInQuad).repeatYoyo(4, 0);

    }

    abstract protected void drawBackground();

    // These need to be renamed.. make sense.
    abstract protected int textPosX();
    abstract protected int textPosY();
    abstract protected int getRightCornerX();
    abstract protected int getLeftCornerY();
    abstract protected int getLeftCornerX();

}
