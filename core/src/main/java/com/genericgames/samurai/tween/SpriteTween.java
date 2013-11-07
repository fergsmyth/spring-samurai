package com.genericgames.samurai.tween;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteTween implements TweenAccessor<Sprite> {

    public static final int ALPHA = 1;
    public static final int FADE_TO_BLACK = 2;
    
    @Override
    public int getValues(Sprite sprite, int tweenType, float[] returnValues) {
        switch (tweenType){
            case ALPHA:
                returnValues[0] = sprite.getColor().a;
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public void setValues(Sprite sprite, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA:
                Gdx.app.log("ALPHA", Float.toString(newValues[0]));
                sprite.setColor(1,1,1,newValues[0]);
                break;
            default:
                break;
        }
    }

}
