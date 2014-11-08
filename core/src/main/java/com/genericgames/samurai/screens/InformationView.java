package com.genericgames.samurai.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InformationView {

    private SpriteBatch batch;
    private BitmapFont font;

    public InformationView(SpriteBatch batch, BitmapFont font){
        this.batch = batch;
        this.font = font;
    }

    public void draw(CharSequence charSequence, float x, float y, float scaleX, float scaleY,
                     BitmapFont.HAlignment alignment, Color fontColor){
        this.reallyDraw(charSequence, x, y, alignment, scaleX, scaleY, fontColor);
    }

    public void draw(CharSequence charSequence, float x, float y, float scaleX, float scaleY,
                     BitmapFont.HAlignment alignment){
        this.draw(charSequence, x, y, scaleX, scaleY, alignment, Color.WHITE);
    }

    public void draw(CharSequence charSequence, float x, float y, float scaleX, float scaleY){
        draw(charSequence, x, y, scaleX, scaleY, BitmapFont.HAlignment.LEFT, Color.WHITE);
    }

    public void draw(CharSequence charSequence, float x, float y){
        draw(charSequence, x, y, 1, 1, BitmapFont.HAlignment.LEFT, Color.WHITE);
    }

    private void reallyDraw(CharSequence charSequence, float x, float y, BitmapFont.HAlignment alignment,
                            float scaleX, float scaleY, Color fontColor) {
        font.setColor(fontColor);

        font.setScale(scaleX, scaleY);
        batch.begin();
        font.drawMultiLine(batch, charSequence, x, y, 0, alignment);
        batch.end();
    }

    public void dispose(){
        font = null;
        batch = null;
    }

}
