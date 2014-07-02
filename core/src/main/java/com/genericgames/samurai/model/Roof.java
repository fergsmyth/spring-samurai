package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Roof extends WorldObject {

    public Roof(float positionX, float positionY){
        super(positionX, positionY);
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {

    }

    @Override
    public String debugInfo() {
        return "Roof\nPos x: "+ getX() +"\nPos y : " + getY();
    }

}
