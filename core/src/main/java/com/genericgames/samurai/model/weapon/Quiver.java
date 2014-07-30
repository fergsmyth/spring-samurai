package com.genericgames.samurai.model.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.audio.SoundEffectCache;
import com.genericgames.samurai.model.Collidable;
import com.genericgames.samurai.model.PlayerCharacter;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.WorldObject;
import com.genericgames.samurai.physics.PhysicalWorldFactory;

public class Quiver extends WorldObject implements Collidable {

    private Texture texture;
    private int numArrowsContained = 3;

    public Quiver(float x, float y, Texture texture, World world){
        super(x, y);
        this.texture = texture;
        body = PhysicalWorldFactory.createQuiverBody(this, world);
    }

    public int getNumArrowsContained() {
        return numArrowsContained;
    }

    public void setNumArrowsContained(int numArrowsContained) {
        this.numArrowsContained = numArrowsContained;
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        batch.draw(texture, getX()-0.25f, getY()-1f, 0.5f, 2);
    }

    @Override
    public String debugInfo() {
        return null;
    }

    public void pickup(SamuraiWorld samuraiWorld, PlayerCharacter player) {
        player.getWeaponInventory().addNumArrows(numArrowsContained);
        samuraiWorld.addObjectToDelete((Quiver) this.body.getUserData());
        SoundEffectCache.pickupQuiver.play(1.0f);

    }
}
