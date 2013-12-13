package com.genericgames.samurai.map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.genericgames.samurai.model.*;
import com.genericgames.samurai.model.movable.living.ai.Enemy;

import java.util.Iterator;

public class LevelLoader {

    private static LevelLoader instance = new LevelLoader();

    private final int tileWidth = 32;
    private final int tileHeight = 32;

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

        addDoors(level, map);
        addEnemies(level, map);
        addPlayerSpawnPoints(level, map);
        addWalls(level, map);
    }

    private void addEnemies(Level level, TiledMap map){
        if(map.getLayers().get("EnemySpawn") != null){
            Iterator<MapObject> iter = map.getLayers().get("EnemySpawn").getObjects().iterator();
            for(; iter.hasNext();){
                MapObject object = iter.next();
                Enemy enemy = new Enemy();
                enemy.setPosition(getX(object), getY(object));
                level.addEnemy(enemy);
            }
        }
    }

    private void addPlayerSpawnPoints(Level level, TiledMap map) {
        Iterator<MapObject> iter = map.getLayers().get("PlayerSpawn").getObjects().iterator();
        for(int i = 0; iter.hasNext(); i++){
            MapObject object = iter.next();
            int x = getX(object);
            int y = getY(object);
            int spawnPosition = Integer.valueOf(object.getProperties().get("Spawn", String.class));
            level.addSpawnPoint(new SpawnPoint(x, y, spawnPosition));
        }
    }

    private void addWalls(Level level, TiledMap map) {
        TiledMapTileLayer wallLayer = (TiledMapTileLayer)map.getLayers().get("Wall");
        for(int x = 0; x <= wallLayer.getWidth(); x++){
            for(int y = 0; y <= wallLayer.getHeight(); y++){
                if(wallLayer.getCell(x,y) != null){
                    Wall wall = new Wall(x, y);
                    level.addWall(wall);
                }
            }
        }
    }

    private void addDoors(Level level, TiledMap map){
        Iterator<MapObject> iter = map.getLayers().get("Door").getObjects().iterator();
        for(int i = 0; iter.hasNext(); i++){
            MapObject doorObj = iter.next();
            int x = getX(doorObj);
            int y = getY(doorObj);
            String levelName = doorObj.getProperties().get("Level", String.class);
            int spawnNumber = Integer.valueOf(doorObj.getProperties().get("Spawn", String.class));
            Door door = new Door(x, y);
            door.setFileName(levelName);
            door.setSpawnNumber(spawnNumber);
            level.addDoor(door);
        }
    }

    private Integer getY(MapObject object) {
        return object.getProperties().get("y", Integer.class) / tileHeight;
    }

    private int getX(MapObject object) {
        return object.getProperties().get("x", Integer.class) / tileWidth;
    }
}
