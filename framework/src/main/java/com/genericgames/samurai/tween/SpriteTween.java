package com.genericgames.samurai.tween;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteTween implements TweenAccessor<Sprite> {

    public static final int ALPHA = 1;
    public static final int SIZE = 2;
    public static final int FADE_TO_BLACK = 2;
    
    @Override
    public int getValues(Sprite sprite, int tweenType, float[] returnValues) {
        switch (tweenType){
            case ALPHA:
                returnValues[0] = sprite.getColor().a;
                return 1;
            case SIZE:
                returnValues[0] = sprite.getScaleX();
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public void setValues(Sprite sprite, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA:
                sprite.setColor(1,1,1,newValues[0]);
                break;
            case SIZE:
                sprite.setScale(newValues[0], newValues[0]);
                break;
            default:
                break;
        }
    }

}
