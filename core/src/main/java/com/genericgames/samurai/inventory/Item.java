package com.genericgames.samurai.inventory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.genericgames.samurai.model.WorldObject;

public class Item extends WorldObject {

    private String imageName;

    public Item(String imageName){
        this.imageName = imageName;
    }

    public String getImageName(){
        return imageName;
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {

    }

    @Override
    public String debugInfo() {
        return "Item\nPos x: "+ getX() +"\nPos y : " + getY();
    }
}
