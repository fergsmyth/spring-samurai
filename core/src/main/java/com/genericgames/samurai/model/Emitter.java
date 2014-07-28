package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.maths.RandomDirFromArc;
import com.genericgames.samurai.model.timeinterval.FixedTimeInterval;
import com.genericgames.samurai.model.timeinterval.TimeInterval;

public class Emitter<T> extends WorldObject {

    private Factory factory;
    private TimeInterval timeInterval;
    private boolean everLasting = false;
    private int maxEmitted = 10;
    private int numberEmitted = 0;
    private int numberEmittedPerInterval = 1;
    private RandomDirFromArc randomDirFromArc;

    public Emitter(Factory factory, float positionX, float positionY){
        super(positionX, positionY);
        this.factory = factory;
        randomDirFromArc = new RandomDirFromArc(new Vector2(0, 0), 1);
        this.timeInterval = new FixedTimeInterval(1000);
    }

    public Emitter(Factory factory, float positionX, float positionY,
                   Vector2 emitVelocity, int emissionArcAngleInDegrees){
        this(factory, positionX, positionY);
        randomDirFromArc = new RandomDirFromArc(emitVelocity, emissionArcAngleInDegrees);
    }

    public Emitter(Factory factory, float positionX, float positionY,
                   Vector2 emitVelocity, int emissionArcAngleInDegrees,
                   boolean everLasting, TimeInterval timeInterval){
        this(factory, positionX, positionY, emitVelocity, emissionArcAngleInDegrees);
        this.everLasting = everLasting;
        this.timeInterval = timeInterval;
    }

    public Emitter(Factory factory, float positionX, float positionY,
                   Vector2 emitVelocity, int emissionArcAngleInDegrees,
                   boolean everLasting, TimeInterval timeInterval, int numberEmittedPerInterval){
        this(factory, positionX, positionY, emitVelocity, emissionArcAngleInDegrees);
        this.everLasting = everLasting;
        this.timeInterval = timeInterval;
        this.numberEmittedPerInterval = numberEmittedPerInterval;
    }

    /**
     * @param samuraiWorld
     * @return true if an object was emitted on this iteration, otherwise false
     */
    public boolean iterate(SamuraiWorld samuraiWorld){
        boolean objectEmitted = false;
        if(timeInterval.hasIntervalEnded() && (everLasting || numberEmitted<maxEmitted)){
            emit(samuraiWorld);
            objectEmitted = true;
            numberEmitted = numberEmitted + numberEmittedPerInterval;
        }
        return objectEmitted;
    }

    public void emit(SamuraiWorld samuraiWorld) {
        for(int i=0; i<numberEmittedPerInterval; i++){
            factory.create(samuraiWorld, this.getX(), this.getY(), randomDirFromArc.getRandomDirection());
        }
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

    public boolean isEverLasting() {
        return everLasting;
    }

    public void setEverLasting(boolean everLasting) {
        this.everLasting = everLasting;
    }

    public int getNumberEmittedPerInterval() {
        return numberEmittedPerInterval;
    }

    public void setNumberEmittedPerInterval(int numberEmittedPerInterval) {
        this.numberEmittedPerInterval = numberEmittedPerInterval;
    }

    public RandomDirFromArc getRandomDirFromArc() {
        return randomDirFromArc;
    }

    public void setRandomDirFromArc(RandomDirFromArc randomDirFromArc) {
        this.randomDirFromArc = randomDirFromArc;
    }

    @Override
    public void setRotation(float angleInRadians){
        super.setRotation(angleInRadians);
        getRandomDirFromArc().getCentralVector().setAngleRad(angleInRadians);
    }

    public void reinitialise(){
        timeInterval.reinitialise();
        numberEmitted = 0;
    }
}
