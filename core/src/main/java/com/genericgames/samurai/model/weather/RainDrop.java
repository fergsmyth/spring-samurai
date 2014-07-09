package com.genericgames.samurai.model.weather;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.model.Factory;
import com.genericgames.samurai.model.Particle;
import com.genericgames.samurai.model.SamuraiWorld;

public class RainDrop extends Particle {

    private static final float LENGTH_SCALAR = 3.0f;

    public RainDrop(float x, float y, Vector2 velocity){
        super(x, y, velocity);
        setMaxLifeTime(30);
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super.draw(batch, shapeRenderer);

        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0.75f, 0.75f, 0.75f, 0.000f);
        shapeRenderer.line(getX(), getY(),
                getX()+(LENGTH_SCALAR*getVelocity().x), getY()+(LENGTH_SCALAR*getVelocity().y));
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
