package com.genericgames.samurai.tween;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenCallback;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class StageActorTween implements TweenAccessor<Stage> {

    public static final int ALPHA = 1;
    
    @Override
    public int getValues(Stage stage, int tweenType, float[] returnValues) {
        switch (tweenType){
            case ALPHA:
                for(Actor actor : stage.getActors()){
                    returnValues[0] = actor.getColor().a;
                    break;
                }
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public void setValues(Stage stage, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA:
                for(Actor actor : stage.getActors()){
                    Color actorColor = actor.getColor();
                    actor.setColor(actorColor.r, actorColor.g, actorColor.b, newValues[0]);
                }
                break;
            default:
                break;
        }
    }
}
