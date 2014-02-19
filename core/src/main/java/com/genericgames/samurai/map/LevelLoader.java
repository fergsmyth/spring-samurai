package com.genericgames.samurai.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.genericgames.samurai.model.*;

public class LevelLoader {

    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    private static LevelLoader instance = new LevelLoader();

    public static LevelLoader getInstance(){
        return instance;
    }

    public void loadLevel(Level level) {
        TiledMap map = new TmxMapLoader().load(level.getLevelFile());
        MapProperties properties = map.getProperties();
        level.setLevelWidth(properties.get(WIDTH, Integer.class));
        level.setLevelHeight(properties.get(HEIGHT, Integer.class));
        level.addDoors(LevelFactory.createDoors(map));
        level.addEnemies(LevelFactory.createEnemies(map));
        level.addNPCs(LevelFactory.createNPCs(map));
        level.addSpawnPoint(LevelFactory.createPlayerSpawns(map));
        level.addWalls(LevelFactory.createWalls(map));
    }

}
