package com.genericgames.samurai.model;

import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.maths.RandomPointFromSpace;
import com.genericgames.samurai.model.timeinterval.TimeInterval;

public class RandomSpaceEmitter<T> extends Emitter<T> {

    private RandomPointFromSpace randomPointFromSpace;

    public RandomSpaceEmitter(Factory factory, float positionX, float positionY, Vector2 emitVelocity,
                              int emissionArcAngleInDegrees, boolean everLasting, TimeInterval timeInterval,
                              RandomPointFromSpace space) {
        super(factory, positionX, positionY, emitVelocity, emissionArcAngleInDegrees, everLasting, timeInterval);
        this.randomPointFromSpace = space;
    }

    public RandomSpaceEmitter(Factory factory, float positionX, float positionY, Vector2 emitVelocity,
                              int emissionArcAngleInDegrees, boolean everLasting, TimeInterval timeInterval,
                              int numberEmittedPerInterval, RandomPointFromSpace space) {
        this(factory, positionX, positionY, emitVelocity, emissionArcAngleInDegrees, everLasting, timeInterval, space);
        this.setNumberEmittedPerInterval(numberEmittedPerInterval);
    }

    public RandomPointFromSpace getRandomPointFromSpace() {
        return randomPointFromSpace;
    }

    public void setRandomPointFromSpace(RandomPointFromSpace randomPointFromSpace) {
        this.randomPointFromSpace = randomPointFromSpace;
    }

    @Override
    public void emit(SamuraiWorld samuraiWorld){
        Vector2 randomPoint;
        for(int i=0; i<getNumberEmittedPerInterval(); i++){
            getFactory().create(samuraiWorld, this.getX(), this.getY(), getRandomDirFromArc().getRandomDirection());
            randomPoint = randomPointFromSpace.getRandomPoint();
            this.setPosition(randomPoint.x, randomPoint.y);
        }
    }

    public void setRandomSpacePosition(float positonX, float positonY){
        this.getRandomPointFromSpace().setPosition(positonX, positonY);
    }
}
