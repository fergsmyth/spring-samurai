package com.genericgames.samurai.ai.patrolpattern;

import com.badlogic.gdx.math.Vector2;

public class QuadPatrolPattern extends PatrolPattern {

    private float quadWidth;
    private float quadHeight;

    public QuadPatrolPattern(Vector2 startingPoint, float quadWidth, float quadHeight) {
        super();
        addPatrolPoint(startingPoint);
        addPatrolPoint(startingPoint.add(quadWidth, 0));
        addPatrolPoint(startingPoint.add(quadWidth, quadHeight));
        addPatrolPoint(startingPoint.add(0, quadHeight));
    }

    public QuadPatrolPattern(Vector2 startingPoint, float quadWidth, float quadHeight, int pauseLength) {
        this(startingPoint, quadWidth, quadHeight, pauseLength, false);
    }

    public QuadPatrolPattern(Vector2 startingPoint, float quadWidth, float quadHeight, int pauseLength,
                             boolean clockwise) {
        super();
        if(clockwise){
            addPatrolPoint(startingPoint.cpy(), pauseLength);
            addPatrolPoint(startingPoint.cpy().add(0, quadHeight), pauseLength);
            addPatrolPoint(startingPoint.cpy().add(quadWidth, quadHeight), pauseLength);
            addPatrolPoint(startingPoint.cpy().add(quadWidth, 0), pauseLength);
        }
        else {
            addPatrolPoint(startingPoint.cpy(), pauseLength);
            addPatrolPoint(startingPoint.cpy().add(quadWidth, 0), pauseLength);
            addPatrolPoint(startingPoint.cpy().add(quadWidth, quadHeight), pauseLength);
            addPatrolPoint(startingPoint.cpy().add(0, quadHeight), pauseLength);
        }
    }

    public float getQuadWidth() {
        return quadWidth;
    }

    public void setQuadWidth(float quadWidth) {
        this.quadWidth = quadWidth;
    }

    public float getQuadHeight() {
        return quadHeight;
    }

    public void setQuadHeight(float quadHeight) {
        this.quadHeight = quadHeight;
    }
}
