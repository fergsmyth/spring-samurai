package com.genericgames.samurai.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class CounterView {

    private ShapeRenderer background;
    private SpriteBatch batch;
    private BitmapFont font;
    private int x;
    private int y;

    public CounterView(int x, int y){
        this.x = x;
        this.y = y;
        background = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    public void draw(CharSequence charSequence){
        batch.begin();
        font.draw(batch, charSequence, x, y + 30);
        font.setScale(1.5f);
        batch.end();
    }

}
