package com.genericgames.samurai.model.weather;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.model.Factory;
import com.genericgames.samurai.model.Particle;
import com.genericgames.samurai.model.SamuraiWorld;

public class RainDrop extends Particle {

    private static final float LENGTH = 0.4f;
    private static final float THICKNESS = 0.03f;

    public RainDrop(float x, float y, Vector2 velocity){
        super(x, y, velocity);
        setMaxLifeTime(20);
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super.draw(batch, shapeRenderer);

        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.75f, 0.75f, 0.75f, 0.5f);
        shapeRenderer.translate(getX()+THICKNESS/2, getY()+LENGTH/2, 0.f);
        shapeRenderer.rotate(0.f, 0.f, 1.f, 90+getRotationInDegrees());
        shapeRenderer.rect(-THICKNESS / 2, -LENGTH / 2, THICKNESS, LENGTH);
        shapeRenderer.end();
    }

    public static class RainDropFactory implements Factory {
        @Override
        public void create(SamuraiWorld samuraiWorld, float x, float y, Vector2 emitVelocity) {
            RainDrop rainDrop = new RainDrop(x, y, emitVelocity);
            samuraiWorld.getParticles().add(rainDrop);
        }
    }
}
