package com.genericgames.samurai.map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.genericgames.samurai.model.*;

import java.util.Iterator;

public class LevelLoader {

    private static LevelLoader instance = new LevelLoader();

    public static LevelLoader getInstance(){
        return instance;
    }

    public void loadLevel(Level level) {
        TiledMap map = new TmxMapLoader().load(level.getFile());
        MapProperties properties = map.getProperties();

        int width = properties.get("width", Integer.class);
        int height = properties.get("height", Integer.class);

        level.setLevelHeight(height);
        level.setLevelWidth(width);

        level.addDoors(LevelFactory.createDoors(map));
        level.addEnemies(LevelFactory.createEnemies(map));
        level.addSpawnPoint(LevelFactory.createPlayerSpawns(map));
        level.addWalls(LevelFactory.createWalls(map));
    }


}
