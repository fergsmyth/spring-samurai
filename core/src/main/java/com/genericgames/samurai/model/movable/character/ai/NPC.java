package com.genericgames.samurai.model.movable.character.ai;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.utility.ImageCache;

import java.util.Map;

public class NPC extends Conversable {

    public NPC(){
        super();
    }

    @Override
    public void draw(SpriteBatch batch) {
        float tileSize = ImageCache.tileSize;
        Map<State, Animation> animationMap = ImageCache.getAnimations().get(getClass());
        TextureRegion texture = animationMap.get(getState()).getKeyFrame(getStateTime(),
                getState().isLoopingState());

        batch.draw(texture, getX() - (tileSize / 2), getY() - (tileSize / 2),
                0.5f, 0.5f, tileSize, tileSize, 1, 1, getRotationInDegrees());
    }
}
