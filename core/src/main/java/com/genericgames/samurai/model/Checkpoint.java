package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.genericgames.samurai.physics.PhysicalWorldFactory;

public class Checkpoint extends WorldObject {

    public Checkpoint(int x, int y){
        super(x, y);
        //Body body = PhysicalWorldFactory.createCheckpointBody(this);
    }
    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {

    }

    @Override
    public String debugInfo() {
        return null;
    }
}
