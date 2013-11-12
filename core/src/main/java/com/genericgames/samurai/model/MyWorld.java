package com.genericgames.samurai.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.levelloader.LevelLoader;
import com.genericgames.samurai.levelloader.LevelLoaderLegend;
import com.genericgames.samurai.model.movable.living.Chest;
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
