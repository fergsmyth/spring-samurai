package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Chest extends WorldObject implements Collidable {

    public Chest(float posX, float posY){
        super(posX, posY);
    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public String debugInfo() {
        return "Door\nPos x: "+ getX() +"\nPos y : " + getY();
    }

}
