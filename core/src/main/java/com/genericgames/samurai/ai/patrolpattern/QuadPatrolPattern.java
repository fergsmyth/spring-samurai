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
