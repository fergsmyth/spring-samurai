package com.genericgames.samurai.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.genericgames.samurai.map.LevelLoader;
import com.genericgames.samurai.model.movable.living.Chest;
import com.genericgames.samurai.model.movable.living.ai.Enemy;
import com.genericgames.samurai.model.movable.living.playable.PlayerCharacter;

import java.util.ArrayList;
import java.util.Collection;

public class Level {
    private int levelHeight;
    private int levelWidth;

    private PlayerCharacter playerCharacter;
    private String levelFile;

    private Collection<Castle> castles;
    private Collection<Chest> chests;
    private Collection<Door> doors;
    private Collection<Enemy> enemies;
    private Collection<SpawnPoint> spawnPoints;
    private Collection<Roof> roofTiles;
    private Collection<Wall> walls;

    public Level(String file, PlayerCharacter character){
        levelFile = file;
        playerCharacter = character;
        castles = new ArrayList<Castle>();
        chests = new ArrayList<Chest>();
        doors = new ArrayList<Door>();
        enemies = new ArrayList<Enemy>();
        spawnPoints = new ArrayList<SpawnPoint>();
        roofTiles = new ArrayList<Roof>();
        walls = new ArrayList<Wall>();
    }

    public void loadLevel(){
        LevelLoader.getInstance().loadLevel(this);
    }

    public String getFile(){
        return levelFile;
    }

    public PlayerCharacter getPlayerCharacter(){
        return playerCharacter;
    }

    public void addDoor(Door door){
        doors.add(door);
    }

    public void addCastle(Castle castle){
        castles.add(castle);
    }

    public void addChest(Chest chest){
        chests.add(chest);
    }

    public void addEnemy(Enemy enemy){
        enemies.add(enemy);
    }

    public void addRoofTiles(Roof roof){
        roofTiles.add(roof);
    }

    public void addSpawnPoint(SpawnPoint spawnPoint){
        spawnPoints.add(spawnPoint);
    }

    public void addWall(Wall wall){
        walls.add(wall);
    }

    public int getLevelHeight(){
        return levelHeight;
    }

    public int getLevelWidth(){
        return levelWidth;
    }

    public void incrementLevelWidth(){
        levelWidth = levelWidth + 1;
    }

    public void setLevelHeight(int height){
        levelHeight = height;
    }

    public void setLevelWidth(int width){
        levelWidth = width;
    }

    public Collection<Door> getDoors() {
        return doors;
    }

    public Collection<Wall> getWalls() {
        return walls;
    }

    public Collection<Roof> getRoofTiles() {
        return roofTiles;
    }

    public Collection<Castle> getCastles() {
        return castles;
    }

    public Collection<Chest> getChests() {
        return chests;
    }

    public Collection<Enemy> getEnemies() {
        return enemies;
    }
}
