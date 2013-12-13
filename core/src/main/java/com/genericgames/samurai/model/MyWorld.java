package com.genericgames.samurai.model;

import java.util.Collection;

import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.model.movable.living.Chest;
import com.genericgames.samurai.model.movable.living.ai.Enemy;
import com.genericgames.samurai.model.movable.living.playable.PlayerCharacter;

public class MyWorld {

    private World physicalWorld;
    private Level currentLevel;

    public MyWorld(Level firstLevel) {
        setCurrentLevel(firstLevel);
    }

    public void setCurrentLevel(Level level) {
        currentLevel = level;
        currentLevel.loadLevel();
    }

    public String getCurrentLevel(){
        return currentLevel.getFile();
    }

    public PlayerCharacter getPlayerCharacter() {
        return currentLevel.getPlayerCharacter();
    }

    public Collection<Castle> getCastles() {
        return currentLevel.getCastles();
    }

    public Collection<Wall> getWalls() {
        return currentLevel.getWalls();
    }

    public Collection<Door> getDoors(){
        return currentLevel.getDoors();
    }

    public Collection<Chest> getChests(){
        return currentLevel.getChests();
    }

    public Collection<Roof> getRoofs(){
        return currentLevel.getRoofTiles();
    }

    public Collection<Enemy> getEnemies(){
        return currentLevel.getEnemies();
    }

    public int getLevelWidth() {
        return currentLevel.getLevelWidth();
    }

    public int getLevelHeight() {
        return currentLevel.getLevelHeight();
    }

    public World getPhysicalWorld() {
        return physicalWorld;
    }

    public void setPhysicalWorld(World physicalWorld){
        this.physicalWorld = physicalWorld;
    }

}
