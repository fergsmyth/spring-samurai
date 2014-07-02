package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

public class CounterView {

    private SpriteBatch batch;
    private BitmapFont font;

    public CounterView(){

        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    public void draw(CharSequence charSequence, Matrix4 uiMatrix, float x, float y){
        //batch.setProjectionMatrix(uiMatrix);
        batch.begin();
        font.draw(batch, charSequence, x, y);
        font.setScale(1f);
        batch.end();
    }

}
