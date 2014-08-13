package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Icon extends WorldObject {

    private Texture texture;
    private float scalingFactorX;
    private float scalingFactorY;

    public Icon(float x, float y, Texture iconName, float scalingFactor){
        super(x,y);
        this.texture = iconName;
        this.scalingFactorX = scalingFactor;
        this.scalingFactorY = scalingFactor;
    }

    public Icon(float x, float y, Texture iconName, float scalingFactorX, float scalingFactorY){
        super(x,y);
        this.texture = iconName;
        this.scalingFactorX = scalingFactorX;
        this.scalingFactorY = scalingFactorY;
    }

    public void setScalingFactors(float scalingFactorX, float scalingFactorY){
        this.scalingFactorX = scalingFactorX;
        this.scalingFactorY = scalingFactorY;
    }

    public float getScalingFactorX() {
        return scalingFactorX;
    }

    public void setScalingFactorX(float scalingFactorX){
        this.scalingFactorX = scalingFactorX;
    }

    public float getScalingFactorY() {
        return scalingFactorY;
    }

    public void setScalingFactorY(float scalingFactorY){
        this.scalingFactorY = scalingFactorY;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        batch.draw(texture, getX(), getY(), scalingFactorX, scalingFactorY);
    }

    @Override
    public String debugInfo() {
        return "Icon\nPos x: "+ getX() +"\nPos y : " + getY();
    }
}
