package com.genericgames.samurai.maths;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class RandomPointFromCircle implements RandomPointFromSpace {

    private Circle circleSpace;
    private Random random = new Random();

    public RandomPointFromCircle(Circle circleSpace){
        this.circleSpace = circleSpace;
    }

    public Circle getCircleSpace() {
        return circleSpace;
    }

    public void setCircleSpace(Circle circleSpace) {
        this.circleSpace = circleSpace;
    }

    @Override
    public Vector2 getRandomPoint() {
        //origin point is bottom-left of Rectangle
        int randomDegrees = random.nextInt(360);
        float randomDistanceFromCentre = (random.nextFloat() * circleSpace.radius);
        float randomX = ((float)Math.cos(Math.toRadians(randomDegrees))*randomDistanceFromCentre)
                + circleSpace.x;
        float randomY = ((float)Math.sin(Math.toRadians(randomDegrees))*randomDistanceFromCentre)
                + circleSpace.y;
        return new Vector2(randomX, randomY);
    }
}
