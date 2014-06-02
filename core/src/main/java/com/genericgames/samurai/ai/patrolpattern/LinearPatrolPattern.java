package com.genericgames.samurai.ai.patrolpattern;

import com.badlogic.gdx.math.Vector2;

public class LinearPatrolPattern extends PatrolPattern {

    public LinearPatrolPattern(Vector2 startingPoint, Vector2 endingPoint) {
        super();
        addPatrolPoint(startingPoint);
        addPatrolPoint(endingPoint);
    }

    public LinearPatrolPattern(Vector2 startingPoint, float length, boolean horizontal, int pauseLength) {
        super();
        addPatrolPoint(startingPoint, pauseLength);
        Vector2 endPoint;
        if(horizontal){
            endPoint = startingPoint.cpy().add(length, 0);
        }
        else {
            endPoint = startingPoint.cpy().add(0, length);
        }
        addPatrolPoint(endPoint, pauseLength);
    }

    public LinearPatrolPattern(Vector2 startingPoint, float length, boolean horizontal) {
        super();
        addPatrolPoint(startingPoint);
        Vector2 endPoint;
        if(horizontal){
            endPoint = startingPoint.add(length, 0);
        }
        else {
            endPoint = startingPoint.add(0, length);
        }
        addPatrolPoint(endPoint);
    }
}
