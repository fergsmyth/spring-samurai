package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.physics.PhysicalWorldFactory;

public class Checkpoint extends WorldObject {

    public Checkpoint(float x, float y, World world, float radius){
        super(x, y);
        //setPosition(x, y);
        PhysicalWorldFactory.createCheckpointBody(this, world, radius);
    }
    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {

    }

    @Override
    public String debugInfo() {
        return null;
    }
}
