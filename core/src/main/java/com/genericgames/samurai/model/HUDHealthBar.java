package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class HUDHealthBar extends WorldObject {

    private Rectangle rectangle;

    public HUDHealthBar(Rectangle rectangle){
        this.rectangle = rectangle;
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String debugInfo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
