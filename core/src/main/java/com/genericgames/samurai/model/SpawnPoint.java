package com.genericgames.samurai.model;

public class SpawnPoint extends WorldObject {

    int spawnNumber;

    public SpawnPoint(int posX, int posY, int spawnNumber){
        super(posX, posY);
        this.spawnNumber = spawnNumber;
    }
}
