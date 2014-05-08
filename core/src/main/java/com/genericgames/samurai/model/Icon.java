package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Icon extends WorldObject {

    private Texture texture;
    private float scalingFactor;

    public Icon(float x, float y, Texture iconName, float scalingFactor){
        super(x,y);
        this.texture = iconName;
        this.scalingFactor = scalingFactor;
    }

    public void setScalingFactor(float scalingFactor){
        this.scalingFactor = scalingFactor;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX(), getY(), 20*scalingFactor, 20*scalingFactor);
    }

    @Override
    public String debugInfo() {
        return "Icon\nPos x: "+ getX() +"\nPos y : " + getY();
    }
}
