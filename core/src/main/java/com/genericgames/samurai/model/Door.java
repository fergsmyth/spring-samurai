package com.genericgames.samurai.model;

public class Door extends WorldObject implements Collidable {

    private String fileName;
    private int spawnNumber;

    public Door(float positionX, float positionY){
        super(positionX, positionY);
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
