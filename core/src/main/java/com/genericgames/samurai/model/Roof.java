package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Roof extends WorldObject {

    public Roof(float positionX, float positionY){
        super(positionX, positionY);
    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public String debugInfo() {
        return "Roof\nPos x: "+ getX() +"\nPos y : " + getY();
    }

}
