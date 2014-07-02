package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.model.movable.Movable;

public class CherryBlossomPetal extends Movable {

    private Vector2 velocity;
    /**
     * The number of frames that this object should exist for.
     */
    private int maxLifeTime = 300;
    private int lifeTime = 0;

    public CherryBlossomPetal(float x, float y, Vector2 velocity){
        super(x, y);
        setSpeed(0.05f);
        this.velocity = velocity;
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.PINK);
        shapeRenderer.rect(getX(), getY(), 0.07f, 0.07f);
        shapeRenderer.end();

        lifeTime++;
    }

    @Override
    public String debugInfo() {
        return null;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public int getMaxLifeTime() {
        return maxLifeTime;
    }
    public void setMaxLifeTime(int maxLifeTime) {
        this.maxLifeTime = maxLifeTime;
    }

    public void updatePosition(SamuraiWorld samuraiWorld){
        Vector2 newPosition = new Vector2(this.getX(), this.getY());
        newPosition = newPosition.add(this.getVelocity());
        this.setPosition(newPosition.x, newPosition.y);
        if(this.getLifeTime() > this.getMaxLifeTime()){
            samuraiWorld.addObjectToDelete(this);
        }
    }

    public static class CherryBlossomPetalFactory implements Factory {
        @Override
        public void create(SamuraiWorld samuraiWorld, float x, float y, Vector2 emitVelocity) {
            CherryBlossomPetal petal = new CherryBlossomPetal(x, y, emitVelocity);
            samuraiWorld.getCherryBlossomPetals().add(petal);
        }
    }
}
