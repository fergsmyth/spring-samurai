package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class BloodParticle extends Particle {

    public BloodParticle(float x, float y, Vector2 velocity) {
        super(x, y, velocity);
        setMaxLifeTime(15);
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super.draw(batch, shapeRenderer);

        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(getX(), getY(), 0.06f, 0.06f);
        shapeRenderer.end();
    }

    public static class BloodParticleFactory implements Factory {
        @Override
        public void create(SamuraiWorld samuraiWorld, float x, float y, Vector2 emitVelocity) {
            BloodParticle bloodParticle = new BloodParticle(x, y, emitVelocity);
            samuraiWorld.getParticles().add(bloodParticle);
        }
    }
}
