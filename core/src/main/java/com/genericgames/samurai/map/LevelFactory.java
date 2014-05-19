package com.genericgames.samurai.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.exception.NoLayerFoundException;
import com.genericgames.samurai.model.*;
import com.genericgames.samurai.model.movable.character.ai.Enemy;
import com.genericgames.samurai.model.movable.character.ai.NPC;

import java.util.ArrayList;
import java.util.Collection;

public class LevelFactory {
    public static final String PLAYER_SPAWN = "PlayerSpawn";
    public static final String ENEMY_SPAWN = "EnemySpawn";
    public static final String NPC_SPAWN = "NPCSpawn";
    public static final String DIALOGUE = "Dialogue";
    public static final String LEVEL = "Level";
    public static final String SPAWN = "Spawn";
    public static final String DOOR = "Door";
    public static final String WALL = "Wall";
    public static final String IMPASSABLE_GATE = "ImpassableGate";
    public static final String X = "x";
    public static final String Y = "y";

    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;

    public static Collection<Enemy> createEnemies(TiledMap map, World world){
        Collection<Enemy> enemies = new ArrayList<Enemy>();
        MapLayer enemyLayer = getLayer(ENEMY_SPAWN, map);
        if(enemyLayer != null){
            for (MapObject object : enemyLayer.getObjects()) {
                Enemy enemy = new Enemy(world, getX(object), getY(object));
                enemies.add(enemy);
            }
        }
        return enemies;
    }

    public static Collection<NPC> createNPCs(TiledMap map, World world){
        Collection<NPC> npcs = new ArrayList<NPC>();
        MapLayer enemyLayer = getLayer(NPC_SPAWN, map);
        if(enemyLayer != null){
            for (MapObject object : enemyLayer.getObjects()) {
                NPC npc = new NPC(world, getX(object), getY(object));
                String dialogue = getStringProperty(object, DIALOGUE);
                npc.setDialogue(dialogue);
                npcs.add(npc);
            }
        }
        return npcs;
    }

    public static PlayerCharacter createPlayer(float playerX, float playerY, World world){
        return new PlayerCharacter(world, playerX, playerY);

    }

    /**
     * @throws NoLayerFoundException if map does not contain the PLAYER_SPAWN layer.
      */
    public static Collection<SpawnPoint> createPlayerSpawns(TiledMap map) {
        Collection<SpawnPoint> spawnPoints = new ArrayList<SpawnPoint>();
        MapLayer layer = getLayer(PLAYER_SPAWN, map);
        if(layer != null){
            for (MapObject object : layer.getObjects()) {
                float x = getX(object);
                float y = getY(object);
                int spawnPosition = getIntegerProperty(object, SPAWN);
                spawnPoints.add(new SpawnPoint(x, y, spawnPosition));
            }
            return spawnPoints;
        }
        throw new NoLayerFoundException(PLAYER_SPAWN);
    }

    public static Collection<Wall> createWalls(TiledMap map, World world) {
        Collection<Wall> walls = new ArrayList<Wall>();
        TiledMapTileLayer wallLayer = (TiledMapTileLayer)map.getLayers().get(WALL);
        for(int x = 0; x <= wallLayer.getWidth(); x++){
            for(int y = 0; y <= wallLayer.getHeight(); y++){
                if(wallLayer.getCell(x,y) != null){
                    walls.add(new Wall(x, y, world));
                }
            }
        }
        return walls;
    }

    public static Collection<ImpassableGate> createImpassableGates(TiledMap map, World world) {
        Collection<ImpassableGate> gates = new ArrayList<ImpassableGate>();
        TiledMapTileLayer impassableGateLayer = (TiledMapTileLayer)map.getLayers().get(IMPASSABLE_GATE);
        for(int x = 0; x <= impassableGateLayer.getWidth(); x++){
            for(int y = 0; y <= impassableGateLayer.getHeight(); y++){
                if(impassableGateLayer.getCell(x,y) != null){
                    gates.add(new ImpassableGate(x, y, world));
                }
            }
        }
        return gates;
    }

    public static Collection<Door> createDoors(TiledMap map, World world){
        Collection<Door> doors = new ArrayList<Door>();
        for (MapObject doorObj : getLayer(DOOR, map).getObjects()) {
            String levelName = getStringProperty(doorObj, LEVEL);
            int spawnNumber = getIntegerProperty(doorObj, SPAWN);
            Door door = new Door(getX(doorObj), getY(doorObj), world);
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

    private static float getX(MapObject object) {
        return object.getProperties().get(X, Float.class) / TILE_WIDTH;
    }

    private static float getY(MapObject object) {
        return object.getProperties().get(Y, Float.class) / TILE_HEIGHT;
    }

}
