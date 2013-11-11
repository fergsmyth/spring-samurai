package com.genericgames.samurai.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.genericgames.samurai.levelloader.LevelLoader;
import com.genericgames.samurai.model.movable.living.Chest;
import com.genericgames.samurai.model.movable.living.playable.PlayerCharacter;

import java.util.ArrayList;
import java.util.Collection;

public class Level {

    private String file;
    private int levelHeight;
    private int levelWidth;

    private PlayerCharacter playerCharacter;

    private Collection<Door> doors;
    private Collection<Wall> walls;
    private Collection<Roof> roofTiles;
    private Collection<Castle> castles;
    private Collection<Chest> chests;
    private Collection<Level> exits;

    public Level(String file, PlayerCharacter character){
        playerCharacter = character;
        this.file = file;
        doors = new ArrayList<Door>();
        walls = new ArrayList<Wall>();
        roofTiles = new ArrayList<Roof>();
        castles = new ArrayList<Castle>();
        chests = new ArrayList<Chest>();
    }

    public void loadLevel(){
        LevelLoader.loadLevel(this);
    }

    public FileHandle getFileHandle(){
        return Gdx.files.local(file);
    }

    public PlayerCharacter getPlayerCharacter(){
        return playerCharacter;
    }

    public void addWall(Wall wall){
        walls.add(wall);
    }

    public void addDoor(Door door){
        doors.add(door);
    }

    public void addRoofTiles(Roof roof){
        roofTiles.add(roof);
    }

    public void addCastle(Castle castle){
        castles.add(castle);
    }

    public void addChest(Chest chest){
        chests.add(chest);
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

}
