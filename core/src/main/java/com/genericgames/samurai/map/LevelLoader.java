package com.genericgames.samurai.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.genericgames.samurai.model.*;

public class LevelLoader {


    private static LevelLoader instance = new LevelLoader();

    public static LevelLoader getInstance(){
        return instance;
    }

    public void loadLevel(Level level) {
        TiledMap map = new TmxMapLoader().load(level.getLevelFile());

        //level.addDoors(LevelFactory.createDoors(map));
        level.addEnemies(LevelFactory.createEnemies(map));
        level.addNPCs(LevelFactory.createNPCs(map));
        level.addSpawnPoint(LevelFactory.createPlayerSpawns(map));
        //level.addWalls(LevelFactory.createWalls(map));
    }

}
