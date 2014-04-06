package com.genericgames.samurai.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PopUpDialogueView extends DialogueView {

    @Override
    protected void drawBackground() {
        drawBorder();
        drawBox();
    }

    @Override
    protected int textPosX() {
        return getLeftCornerX() + speakerIcon.getWidth();
    }

    @Override
    protected int textPosY() {
        return getLeftCornerY() + speakerIcon.getHeight() / 2;
    }

    private void drawBorder() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(getLeftCornerX() - 1, getLeftCornerY() - 1, getBoxWidth() + 1, getBoxHeight() + 1);
        shapeRenderer.end();
    }

    private void drawBox() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(getLeftCornerX(), getLeftCornerY(), getBoxWidth(), getBoxHeight());
        shapeRenderer.end();
    }

    private int getBoxHeight() {
        return Gdx.graphics.getHeight() / 5;
    }

    private int getBoxWidth() {
        return Gdx.graphics.getWidth() / 2;
    }

    @Override
    protected int getRightCornerX() {
        return 0;
    }

    @Override
    protected int getLeftCornerY() {
        return Gdx.graphics.getHeight() / 17;
    }

    @Override
    protected int getLeftCornerX() { return Gdx.graphics.getWidth() / 9;  }
}
