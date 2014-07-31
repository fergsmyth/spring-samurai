package com.genericgames.samurai.model.weapon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.audio.SoundEffectCache;
import com.genericgames.samurai.model.*;
import com.genericgames.samurai.physics.PhysicalWorldFactory;
import com.genericgames.samurai.utility.ImageCache;

public class Quiver extends WorldObject implements Collidable {

    private int numArrowsContained = 3;

    public Quiver(float x, float y, World world){
        super(x, y);
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
        batch.draw(ImageCache.quiver, getX()-0.25f, getY()-1f, 0.5f, 2);
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

    public static class QuiverFactory implements Factory {

        @Override
        public void create(SamuraiWorld samuraiWorld, float x, float y, Vector2 emitVelocity) {
            Quiver quiver = new Quiver(x, y, samuraiWorld.getPhysicalWorld());
            samuraiWorld.getQuivers().add(quiver);
        }
    }
}
