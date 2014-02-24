package com.genericgames.samurai.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.genericgames.samurai.Dialogue;
import com.genericgames.samurai.DialogueLoader;
import com.genericgames.samurai.exception.NoLayerFoundException;
import com.genericgames.samurai.model.Door;
import com.genericgames.samurai.model.SpawnPoint;
import com.genericgames.samurai.model.Wall;
import com.genericgames.samurai.model.movable.living.ai.Enemy;
import com.genericgames.samurai.model.movable.living.ai.NPC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class LevelFactory {
    public static final String PLAYER_SPAWN = "PlayerSpawn";
    public static final String ENEMY_SPAWN = "EnemySpawn";
    public static final String NPC_SPAWN = "NPCSpawn";
    public static final String DIALOGUE = "Dialogue";
    public static final String LEVEL = "Level";
    public static final String SPAWN = "Spawn";
    public static final String DOOR = "Door";
    public static final String WALL = "Wall";
    public static final String X = "x";
    public static final String Y = "y";

    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;

    public static Collection<Enemy> createEnemies(TiledMap map){
        Collection<Enemy> enemies = new ArrayList<Enemy>();
        MapLayer enemyLayer = getLayer(ENEMY_SPAWN, map);
        if(enemyLayer != null){
            for(Iterator<MapObject> iter = enemyLayer.getObjects().iterator(); iter.hasNext();){
                MapObject object = iter.next();
                Enemy enemy = new Enemy();
                enemy.setPosition(getX(object), getY(object));
//                Dialogue enemyDialogue = DialogueLoader.loader().loadDialogue(getStringProperty(object, DIALOGUE));
//                enemy.setDialogue(enemyDialogue);
                enemies.add(enemy);
            }
        }
        return enemies;
    }

    public static Collection<NPC> createNPCs(TiledMap map){
        Collection<NPC> npcs = new ArrayList<NPC>();
        MapLayer enemyLayer = getLayer(NPC_SPAWN, map);
        if(enemyLayer != null){
            for(Iterator<MapObject> iter = enemyLayer.getObjects().iterator(); iter.hasNext();){
                MapObject object = iter.next();
                NPC npc = new NPC();
                npc.setPosition(getX(object), getY(object));
                String dialogue = getStringProperty(object, DIALOGUE);
                npc.setDialogue(dialogue);
                npcs.add(npc);
            }
        }
        return npcs;
    }

    /**
     * @throws NoLayerFoundException if map does not contain the PLAYER_SPAWN layer.
      */
    public static Collection<SpawnPoint> createPlayerSpawns(TiledMap map) {
        Collection<SpawnPoint> spawnPoints = new ArrayList<SpawnPoint>();
        MapLayer layer = getLayer(PLAYER_SPAWN, map);
        if(layer != null){
            for(Iterator<MapObject> iter = layer.getObjects().iterator(); iter.hasNext();){
                MapObject object = iter.next();
                int x = getX(object);
                int y = getY(object);
                int spawnPosition = getIntegerProperty(object, SPAWN);
                spawnPoints.add(new SpawnPoint(x, y, spawnPosition));
            }
            return spawnPoints;
        }
        throw new NoLayerFoundException(PLAYER_SPAWN);
    }

    public static Collection<Wall> createWalls(TiledMap map) {
        Collection<Wall> walls = new ArrayList<Wall>();
        TiledMapTileLayer wallLayer = (TiledMapTileLayer)map.getLayers().get(WALL);
        for(int x = 0; x <= wallLayer.getWidth(); x++){
            for(int y = 0; y <= wallLayer.getHeight(); y++){
                if(wallLayer.getCell(x,y) != null){
                    walls.add(new Wall(x, y));
                }
            }
        }
        return walls;
    }

    public static Collection<Door> createDoors(TiledMap map){
        Collection<Door> doors = new ArrayList<Door>();
        for(Iterator<MapObject> iter = getLayer(DOOR, map).getObjects().iterator(); iter.hasNext();){
            MapObject doorObj = iter.next();
            String levelName = getStringProperty(doorObj, LEVEL);
            int spawnNumber = getIntegerProperty(doorObj, SPAWN);
            Door door = new Door(getX(doorObj), getY(doorObj));
            door.setFileName(levelName);
            door.setSpawnNumber(spawnNumber);
            doors.add(door);
        }
        return doors;
    }

    private static MapLayer getLayer(String layerName, TiledMap map) {
        return map.getLayers().get(layerName);
    }

    private static Integer getIntegerProperty(MapObject object, String propertyName) {
        return Integer.valueOf(getStringProperty(object, propertyName));
    }

    private static String getStringProperty(MapObject object, String propertyName) {
        return object.getProperties().get(propertyName, String.class);
    }

    private static int getX(MapObject object) {
        return Math.round(object.getProperties().get(X, Float.class)) / TILE_WIDTH;
    }

    private static Integer getY(MapObject object) {
        return Math.round(object.getProperties().get(Y, Float.class)) / TILE_HEIGHT;
    }

}
