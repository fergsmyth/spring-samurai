package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Emitter<T> extends WorldObject {

    private Factory factory;

    private int timeInterval = 1000;
    private int timeCounter = 0;
    private int maxEmitted = 10;
    private int numberEmitted = 0;

    public Emitter(Factory factory, float positionX, float positionY){
        super(positionX, positionY);
        this.factory = factory;
    }

    public void iterate(SamuraiWorld samuraiWorld){
        if((timeCounter%timeInterval==0) && (numberEmitted<maxEmitted)){
            emit(samuraiWorld);
            numberEmitted++;
        }
        timeCounter++;
    }

    private void emit(SamuraiWorld samuraiWorld) {
        factory.create(samuraiWorld, this.getX(), this.getY());
    }

    @Override
    public void draw(SpriteBatch batch) {
        //Nothing to draw
    }

    @Override
    public String debugInfo() {
        return "Emitter\nPos x: "+ getX() +"\nPos y : " + getY()+"\nRotation : " + getRotationInDegrees();
    }
}
