package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class CounterView {

    private ShapeRenderer background;
    private SpriteBatch batch;
    private BitmapFont font;
    private float x;
    private float y;

    public CounterView(float x, float y){
        this.x = x;
        this.y = y;
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    public void draw(CharSequence charSequence){
        batch.begin();
        font.draw(batch, charSequence, Gdx.graphics.getWidth() - 15, Gdx.graphics.getHeight());
        font.setScale(1.5f);
        batch.end();
    }

}
