package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Chest extends WorldObject implements Collidable {

    public Chest(float posX, float posY){
        super(posX, posY);
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {

    }

    @Override
    public String debugInfo() {
        return "Door\nPos x: "+ getX() +"\nPos y : " + getY();
    }

}
