package com.genericgames.samurai.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class OverlayDialogueView extends DialogueView {


    public OverlayDialogueView(SpriteBatch batch, ShapeRenderer shapeRenderer) {
            super(batch, shapeRenderer);
    }

    @Override
    protected void drawBackground() {
        drawBorder();
        drawBox();
    }

    @Override
    protected int textPosX() {
        return getLeftCornerX() + speakerIcon.getWidth() * 2;
    }

    @Override
    protected int textPosY() {
        return getLeftCornerY() + speakerIcon.getHeight();
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

    protected int getRightCornerX(){
        return getLeftCornerX() + getBoxWidth();
    }

    protected int getLeftCornerY() {
        return Gdx.graphics.getHeight() / 3;
    }

    protected int getLeftCornerX() {
        return Gdx.graphics.getWidth() / 4;
    }

}
