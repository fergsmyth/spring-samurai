package com.genericgames.samurai.maths;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class RandomPointFromRect implements RandomPointFromSpace {

    private Rectangle rectSpace;
    private Random random = new Random();

    public RandomPointFromRect(Rectangle rectSpace){
        this.rectSpace = rectSpace;
    }

    public Rectangle getRectSpace() {
        return rectSpace;
    }

    public void setRectSpace(Rectangle rectSpace) {
        this.rectSpace = rectSpace;
    }

    @Override
    public Vector2 getRandomPoint() {
        //origin point is bottom-left of Rectangle
        float randomX = (random.nextFloat() * rectSpace.getWidth())
                + rectSpace.getX();
        float randomY = (random.nextFloat() * rectSpace.getHeight())
                + rectSpace.getY();
        return new Vector2(randomX, randomY);
    }

    @Override
    public void setPosition(float positionX, float positionY) {
        rectSpace.setPosition(positionX, positionY);
    }
}
