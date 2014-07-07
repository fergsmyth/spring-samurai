package com.genericgames.samurai.model;

import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.maths.RandomPointFromSpace;
import com.genericgames.samurai.model.timeinterval.TimeInterval;

public class RandomSpaceEmitter<T> extends Emitter<T> {

    private RandomPointFromSpace randomPointFromSpace;

    public RandomSpaceEmitter(Factory factory, float positionX, float positionY, RandomPointFromSpace space) {
        super(factory, positionX, positionY);
        this.randomPointFromSpace = space;
    }

    public RandomSpaceEmitter(Factory factory, float positionX, float positionY, Vector2 emitVelocity,
                              int emissionArcAngleInDegrees, RandomPointFromSpace space) {
        super(factory, positionX, positionY, emitVelocity, emissionArcAngleInDegrees);
        this.randomPointFromSpace = space;
    }

    public RandomSpaceEmitter(Factory factory, float positionX, float positionY, Vector2 emitVelocity,
                              int emissionArcAngleInDegrees, boolean everLasting, TimeInterval timeInterval,
                              RandomPointFromSpace space) {
        super(factory, positionX, positionY, emitVelocity, emissionArcAngleInDegrees, everLasting, timeInterval);
        this.randomPointFromSpace = space;
    }

    public RandomPointFromSpace getRandomPointFromSpace() {
        return randomPointFromSpace;
    }

    public void setRandomPointFromSpace(RandomPointFromSpace randomPointFromSpace) {
        this.randomPointFromSpace = randomPointFromSpace;
    }

    @Override
    public void emit(SamuraiWorld samuraiWorld){
        super.emit(samuraiWorld);
        Vector2 randomPoint = randomPointFromSpace.getRandomPoint();
        this.setPosition(randomPoint.x, randomPoint.y);
    }
}
