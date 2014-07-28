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
        font.setScale(1f);
        reallyDraw(charSequence, x, y, BitmapFont.HAlignment.LEFT);
    }

    public void draw(CharSequence charSequence, float x, float y, float scale,
                     BitmapFont.HAlignment alignment){
        font.setScale(scale);
        this.reallyDraw(charSequence, x, y, alignment);
    }

    private void reallyDraw(CharSequence charSequence, float x, float y, BitmapFont.HAlignment alignment) {
        batch.begin();
        font.drawMultiLine(batch, charSequence, x, y, 0, alignment);
        batch.end();
    }

    public void dispose(){
        font = null;
        batch = null;
    }

}
