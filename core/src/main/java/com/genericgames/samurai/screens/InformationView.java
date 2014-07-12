package com.genericgames.samurai.screens;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InformationView {

    private SpriteBatch batch;
    private BitmapFont font;

    public InformationView(SpriteBatch batch, BitmapFont font){
        this.batch = batch;
        this.font = font;
    }

    public void draw(CharSequence charSequence, float x, float y){
        batch.begin();
        font.draw(batch, charSequence, x, y);
        font.setScale(1f);
        batch.end();
    }

    public void dispose(){
        font = null;
        batch = null;
    }

}
