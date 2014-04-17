package com.genericgames.samurai.model;

import java.util.Collection;

import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.model.movable.character.ai.Enemy;
import com.genericgames.samurai.model.movable.character.ai.NPC;
import com.genericgames.samurai.physics.Arrow;

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

    public void addArrow(Arrow arrow){ currentLevel.addArrow(arrow); }

    public void removeArrow(Arrow removeArrow){ currentLevel.removeArrow(removeArrow);}

    public Collection<Arrow> getArrows() {
        return currentLevel.getArrows();
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
        return currentLevel.getDoorPosition(position);
    }

    public int getLevelWidth() {
        return currentLevel.getLevelWidth();
    }

    public int getLevelHeight() {
        return currentLevel.getLevelHeight();
    }

    public World getPhysicalWorld() {
        return currentLevel.getPhysicsWorld();
    }

    public void setPhysicalWorld(World physicalWorld){
        this.currentLevel.setPhysicsWorld(physicalWorld);
    }

    public void addObjectToDelete(WorldObject worldObject){ this.currentLevel.addObjectToDelete(worldObject);}

    public void deleteWorldObjects(){ this.currentLevel.deleteWorldObjects();}

}
