package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.model.movable.Movable;

public abstract class Particle extends Movable {

    private Vector2 velocity;
    /**
     * The number of frames that this object should exist for.
     */
    private int maxLifeTime = 300;
    private int lifeTime = 0;

    public Particle(float x, float y, Vector2 velocity){
        super(x, y);
        this.velocity = velocity;
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
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

    public void update(SamuraiWorld samuraiWorld){
        updatePosition();
        if(this.getLifeTime() > this.getMaxLifeTime()){
            samuraiWorld.addObjectToDelete(this);
        }
        lifeTime++;
    }

    private void updatePosition() {
        Vector2 newPosition = new Vector2(this.getX(), this.getY());
        newPosition = newPosition.add(this.getVelocity());
        this.setPosition(newPosition.x, newPosition.y);
    }
}
