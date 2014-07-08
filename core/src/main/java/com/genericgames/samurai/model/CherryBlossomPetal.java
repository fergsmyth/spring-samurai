package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class CherryBlossomPetal extends Particle {

    public CherryBlossomPetal(float x, float y, Vector2 velocity){
        super(x, y, velocity);
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super.draw(batch, shapeRenderer);

        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.PINK);
        shapeRenderer.rect(getX(), getY(), 0.07f, 0.07f);
        shapeRenderer.end();
    }

    public static class CherryBlossomPetalFactory implements Factory {
        @Override
        public void create(SamuraiWorld samuraiWorld, float x, float y, Vector2 emitVelocity) {
            CherryBlossomPetal petal = new CherryBlossomPetal(x, y, emitVelocity);
            samuraiWorld.getParticles().add(petal);
        }
    }
}
