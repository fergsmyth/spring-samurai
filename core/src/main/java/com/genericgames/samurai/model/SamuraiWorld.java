package com.genericgames.samurai.model;

import java.util.Collection;

import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.model.movable.living.Chest;
import com.genericgames.samurai.model.movable.living.ai.Enemy;
import com.genericgames.samurai.model.movable.living.ai.NPC;

public class SamuraiWorld {

    private Level currentLevel;

    public SamuraiWorld(Level firstLevel) {
        currentLevel = firstLevel;
    }

    public Level getCurrentLevel(){
        return currentLevel;
    }

    public void setCurrentLevel(Level level){
        currentLevel = level;
    }

    public String getCurrentLevelFile(){
        return currentLevel.getLevelFile();
    }

    public PlayerCharacter getPlayerCharacter() {
        return currentLevel.getPlayerCharacter();
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

    public Collection<NPC> getNPCs(){
        return currentLevel.getNPCs();
    }

    public SpawnPoint getSpawnPointByPosition(int position){
        return currentLevel.getSpawnPointByPosition(position);
    }

    public int getLevelWidth() {
        return currentLevel.getLevelWidth();
    }

    public int getLevelHeight() {
        return currentLevel.getLevelHeight();
    }

    public World getPhysicalWorld() {
        return currentLevel.getPhysicalWorld();
    }

    public void setPhysicalWorld(World physicalWorld){
        this.currentLevel.setPhysicalWorld(physicalWorld);
    }

}
