package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class WorldObject {
    private float positionX;
    private float positionY;
    private float angleInRadians;
    protected Body body;

    public WorldObject(){
        this(0, 0);
    }

    public WorldObject(float positionX, float positionY){
        setPositionX(positionX);
        setPositionY(positionY);
    }

    public WorldObject(float positionX, float positionY, float angleInRadians){
        setPositionX(positionX);
        setPositionY(positionY);
        setRotation(angleInRadians);
    }

    public float getRotation(){
        return angleInRadians;
    }

    public void setRotation(float angleInRadians){
        this.angleInRadians = angleInRadians;
    }

    public void setBody(Body body){
        this.body = body;
    }

    public float getRotationInDegrees(){
        //System.out.println(debugInfo());
        return MathUtils.radiansToDegrees * angleInRadians;
    }

    public void setPosition(float positonX, float positonY){
        setPositionX(positonX);
        setPositionY(positonY);
    }

    public float getX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public void deleteBody(World world){
        if (body != null) {
            world.destroyBody(body);
            body = null;
        }
    }

    public abstract void draw(SpriteBatch batch, ShapeRenderer shapeRenderer);

    abstract public String debugInfo();

}