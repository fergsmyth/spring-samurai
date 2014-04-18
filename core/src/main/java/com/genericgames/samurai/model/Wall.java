package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Wall extends WorldObject implements Collidable {

    public Wall(int positionX, int positionY){
        super(positionX, positionY);
	}

    @Override
    public void draw(SpriteBatch batch) {

    }
}