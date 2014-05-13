package com.genericgames.samurai.model.movable.character.ai;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.physics.PhysicalWorldFactory;
import com.genericgames.samurai.utility.ImageCache;

import java.util.Map;

/**
 * Extend Living if NPC needs to be "killable".
 * Extend Combatable if NPC needs to fight.
 */
public class NPC extends Conversable {

    public NPC(World world, float x, float y){
        super();
        setPosition(x, y);
        body = PhysicalWorldFactory.createNPC(this, world);
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

    @Override
    public String debugInfo() {
        return "NPC\nPos x: "+ getX() +"\nPos y : " + getY();
    }
}
