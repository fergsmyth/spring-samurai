package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpawnPoint extends WorldObject {

    int spawnNumber;

    public SpawnPoint(float posX, float posY, int spawnNumber){
        super(posX, posY);
        this.spawnNumber = spawnNumber;
    }

    public int getSpawnNumber(){
        return spawnNumber;
    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public String debugInfo() {
        return "Spawn point\nPos x: "+ getX() +"\nPos y : " + getY();
    }
}
