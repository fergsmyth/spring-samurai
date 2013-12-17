package com.genericgames.samurai.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.map.LevelLoader;
import com.genericgames.samurai.model.movable.living.Chest;
import com.genericgames.samurai.model.movable.living.ai.Enemy;
import com.genericgames.samurai.model.movable.living.playable.PlayerCharacter;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Level implements Serializable {
    private int levelHeight;
    private int levelWidth;

    private PlayerCharacter playerCharacter;
    private String levelFile;

    private Collection<Chest> chests;
    private Collection<Door> doors;
    private Collection<Enemy> enemies;
    private Collection<SpawnPoint> spawnPoints;
    private Collection<Roof> roofTiles;
    private Collection<Wall> walls;

    public Level(String file, PlayerCharacter character){
        levelFile = file;
        playerCharacter = character;
        chests = new ArrayList<Chest>();
        doors = new ArrayList<Door>();
        enemies = new ArrayList<Enemy>();
        spawnPoints = new ArrayList<SpawnPoint>();
        roofTiles = new ArrayList<Roof>();
        walls = new ArrayList<Wall>();
        loadLevel();
    }

    public void loadLevel(){
        LevelLoader.getInstance().loadLevel(this);
    }

    public String getLevelFile(){
        return levelFile;
    }

    public PlayerCharacter getPlayerCharacter(){
        return playerCharacter;
    }

    public void addDoors(Collection<Door> doors){
        this.doors.addAll(doors);
    }

    public void addEnemies(Collection<Enemy> enemies){
        this.enemies.addAll(enemies);
    }

    public void addSpawnPoint(Collection<SpawnPoint> spawnPoints){
        this.spawnPoints.addAll(spawnPoints);
    }

    public void addWalls(Collection<Wall> walls){
        this.walls.addAll(walls);
    }

    public int getLevelHeight(){
        return levelHeight;
    }

    public int getLevelWidth(){
        return levelWidth;
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

    public Collection<Chest> getChests() {
        return chests;
    }

    public Collection<Enemy> getEnemies() {
        return enemies;
    }

    public SpawnPoint getSpawnPointByPosition(int position){
        for(SpawnPoint point : spawnPoints){
            if (point.getSpawnNumber() == position){
                return point;
            }
        }
        //TODO Make this an exception
        Gdx.app.log("Level", "No spawn found for position " +  position);
        return new SpawnPoint(1, 1, 0);
    }

    private void readObject(ObjectInputStream stream)
            throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    private Object writeReplace() {
        return new LevelProxy(this);
    }

    private static class LevelProxy implements Serializable {

        Vector2 vector2;
        private float playerX;
        private float playerY;
        private String levelFile;

        public LevelProxy(Level level){
            playerX = level.getPlayerCharacter().getX();
            playerY = level.getPlayerCharacter().getY();
            vector2 = new Vector2(playerX, playerY);
            this.levelFile = level.getLevelFile();
        }

        private Object readResolve() throws ObjectStreamException {
            PlayerCharacter character = new PlayerCharacter();
            character.setPosition(playerX, playerY);
            return new Level(levelFile, character);
        }

    }
}
