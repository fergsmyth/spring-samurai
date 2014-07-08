package com.genericgames.samurai.model.weather;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.model.Factory;
import com.genericgames.samurai.model.Particle;
import com.genericgames.samurai.model.SamuraiWorld;

public class SnowFlake extends Particle {

    public SnowFlake(float x, float y, Vector2 velocity){
        super(x, y, velocity);
        setMaxLifeTime(60);
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        super.draw(batch, shapeRenderer);

        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(getX(), getY(), 0.09f, 0.09f);
        shapeRenderer.end();
    }

    public static class SnowFlakeFactory implements Factory {
        @Override
        public void create(SamuraiWorld samuraiWorld, float x, float y, Vector2 emitVelocity) {
            SnowFlake snowFlake = new SnowFlake(x, y, emitVelocity);
            samuraiWorld.getParticles().add(snowFlake);
        }
    }
}
