package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.model.timeinterval.FixedTimeInterval;
import com.genericgames.samurai.model.timeinterval.TimeInterval;

public class Emitter<T> extends WorldObject {

    private Factory factory;
    private TimeInterval timeInterval;
    private boolean everLasting = false;
    private int maxEmitted = 10;
    private int numberEmitted = 0;
    private Vector2 emitVelocity;

    public Emitter(Factory factory, float positionX, float positionY){
        super(positionX, positionY);
        this.factory = factory;
        this.emitVelocity = new Vector2(0, 0);
        this.timeInterval = new FixedTimeInterval(1000);
    }

    public Emitter(Factory factory, float positionX, float positionY, Vector2 emitVelocity){
        this(factory, positionX, positionY);
        this.emitVelocity = emitVelocity;
    }

    public Emitter(Factory factory, float positionX, float positionY, Vector2 emitVelocity,
                   boolean everLasting, TimeInterval timeInterval){
        this(factory, positionX, positionY, emitVelocity);
        this.everLasting = everLasting;
        this.timeInterval = timeInterval;
    }

    public void iterate(SamuraiWorld samuraiWorld){
        if(timeInterval.hasIntervalEnded() && (everLasting || numberEmitted<maxEmitted)){
            emit(samuraiWorld);
            numberEmitted++;
        }
    }

    private void emit(SamuraiWorld samuraiWorld) {
        factory.create(samuraiWorld, this.getX(), this.getY(), emitVelocity);
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        //Nothing to draw
    }

    @Override
    public String debugInfo() {
        return "Emitter\nPos x: "+ getX() +"\nPos y : " + getY()+"\nRotation : " + getRotationInDegrees();
    }

    public Factory getFactory() {
        return factory;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }

    public int getMaxEmitted() {
        return maxEmitted;
    }

    public void setMaxEmitted(int maxEmitted) {
        this.maxEmitted = maxEmitted;
    }

    public int getNumberEmitted() {
        return numberEmitted;
    }

    public void setNumberEmitted(int numberEmitted) {
        this.numberEmitted = numberEmitted;
    }

    public Vector2 getEmitVelocity() {
        return emitVelocity;
    }

    public void setEmitVelocity(Vector2 emitVelocity) {
        this.emitVelocity = emitVelocity;
    }

    public boolean isEverLasting() {
        return everLasting;
    }

    public void setEverLasting(boolean everLasting) {
        this.everLasting = everLasting;
    }
}
