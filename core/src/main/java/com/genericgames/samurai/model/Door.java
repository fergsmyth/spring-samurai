package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Door extends WorldObject implements Collidable {

    private String fileName;
    private int spawnNumber;

    public Door(float positionX, float positionY){
        super(positionX, positionY);
    }

    @Override
    public void draw(SpriteBatch batch) {

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
