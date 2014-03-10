package com.genericgames.samurai.model;

public class SpawnPoint extends WorldObject {

    int spawnNumber;

    public SpawnPoint(float posX, float posY, int spawnNumber){
        super(posX, posY);
        this.spawnNumber = spawnNumber;
    }

    public int getSpawnNumber(){
        return spawnNumber;
    }
}
