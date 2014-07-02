package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.physics.PhysicalWorldFactory;
import com.genericgames.samurai.physics.PhysicalWorldHelper;

public class Door extends WorldObject implements Collidable {

    private String fileName;
    private int spawnNumber;

    public Door(float positionX, float positionY, World world){
        super(positionX, positionY);
        body = PhysicalWorldFactory.createStaticPhysicalWorldObject(this, world);
        PhysicalWorldFactory.createRectangleFixture(body, 0.5f, 0.25f,
                PhysicalWorldHelper.CATEGORY_INDESTRUCTIBLE);
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {

    }

    @Override
    public String debugInfo() {
        return "Door\nPos x: "+ getX() +"\nPos y : " + getY();
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return fileName;
    }

    public void setSpawnNumber(int spawnNumber){
        this.spawnNumber = spawnNumber;
    }

    public int getSpawnNumber(){
        return spawnNumber;
    }
}
