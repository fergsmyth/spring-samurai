package com.genericgames.samurai.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.ai.patrolpattern.LinearPatrolPattern;
import com.genericgames.samurai.ai.patrolpattern.QuadPatrolPattern;
import com.genericgames.samurai.exception.NoLayerFoundException;
import com.genericgames.samurai.model.*;
import com.genericgames.samurai.model.movable.character.ai.AI;
import com.genericgames.samurai.model.movable.character.ai.Enemy;
import com.genericgames.samurai.model.movable.character.ai.NPC;
import com.genericgames.samurai.utility.ImageCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class LevelFactory {
    public static final String PLAYER_SPAWN = "PlayerSpawn";
    public static final String ENEMY_SPAWN = "EnemySpawn";
    public static final String ENEMY_EMITTER = "EnemyEmitter";
    public static final String NPC_SPAWN = "NPCSpawn";
    public static final String CHECKPOINT = "Checkpoint";
    public static final String DIALOGUE = "Dialogue";
    public static final String PATROL_PATTERN_GROUP = "PatrolPatternGroup";
    public static final String QUAD_PATROL_PATTERN = "QuadPatrolPattern";
    public static final String PAUSE = "Pause";
    public static final String HEIGHT = "Height";
    public static final String WIDTH = "Width";
    public static final String CLOCKWISE = "Clockwise";
    public static final String LINE_PATROL_PATTERN = "LinePatrolPattern";
    public static final String LENGTH = "Length";
    public static final String HORIZONTAL = "Horizontal";
    public static final String LEVEL = "Level";
    public static final String SPAWN = "Spawn";
    public static final String DOOR = "Door";
    public static final String WALL = "Wall";
    public static final String IMPASSABLE_GATE = "ImpassableGate";
    public static final String CHERRY_BLOSSOM = "CherryBlossom";
    public static final String X = "x";
    public static final String Y = "y";

    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;

    public static Collection<Enemy> createEnemies(TiledMap map, World world){
        Collection<Enemy> enemies = new ArrayList<Enemy>();
        MapLayer enemyLayer = getLayer(ENEMY_SPAWN, map);
        if(enemyLayer != null){
            for (MapObject object : enemyLayer.getObjects()) {
                Enemy enemy = new Enemy(world, getNPCPositionX(object), getNPCPositionY(object));

                String patrolPatternGroupName = getStringProperty(object, PATROL_PATTERN_GROUP);
                addPatrolPatternGroup(patrolPatternGroupName, enemy, map);

                enemies.add(enemy);
            }
        }
        return enemies;
    }

    private static void addPatrolPatternGroup(String patrolPatternGroupName, AI ai, TiledMap map) {
        MapLayer patrolPatternGroupLayer = getLayer(PATROL_PATTERN_GROUP, map);
        if(patrolPatternGroupLayer != null){
            MapObject patternGroup = getObject(patrolPatternGroupName, patrolPatternGroupLayer);

            if(patternGroup != null) {
                Iterator<Object> patternGroupIterator = patternGroup.getProperties().getValues();
                while(patternGroupIterator.hasNext()){
                    Object next = patternGroupIterator.next();
                    if(next instanceof String){
                        String patternName = (String) next;
                        addPatrolPattern(ai, map, patternName);
                    }
                }
            }
        }
    }

    private static void addPatrolPattern(AI ai, TiledMap map, String patternName) {
        if(patternName.contains(LINE_PATROL_PATTERN)){
            addLinearPatrolPattern(patternName, ai, map);
        }
        else if(patternName.contains(QUAD_PATROL_PATTERN)) {
            addQuadPatrolPattern(patternName, ai, map);
        }
    }

    private static void addQuadPatrolPattern(String quadPatrolPatternName, AI ai, TiledMap map) {
        MapLayer quadPatrolPatternLayer = getLayer(QUAD_PATROL_PATTERN, map);
        if(quadPatrolPatternLayer != null){
            MapObject quadPattern = getObject(quadPatrolPatternName, quadPatrolPatternLayer);

            if(quadPattern != null) {
                float quadWidth = getFloatProperty(quadPattern, WIDTH);
                float quadHeight = getFloatProperty(quadPattern, HEIGHT);
                int pauseInterval = getIntegerProperty(quadPattern, PAUSE);
                boolean clockwise = getBooleanProperty(quadPattern, CLOCKWISE);
                ai.getPatrolPatternGroup().addPatrolPattern(new QuadPatrolPattern(
                        new Vector2(ai.getX(), ai.getY()), quadWidth, quadHeight, pauseInterval, clockwise));
            }
        }
    }

    private static void addLinearPatrolPattern(String linearPatrolPatternName, AI ai, TiledMap map) {
        MapLayer linearPatrolPatternLayer = getLayer(LINE_PATROL_PATTERN, map);
        if(linearPatrolPatternLayer != null){
            MapObject linePattern = getObject(linearPatrolPatternName, linearPatrolPatternLayer);

            if(linePattern != null) {
                float length = getFloatProperty(linePattern, LENGTH);
                int pauseInterval = getIntegerProperty(linePattern, PAUSE);
                boolean horizontal = getBooleanProperty(linePattern, HORIZONTAL);
                ai.getPatrolPatternGroup().addPatrolPattern(new LinearPatrolPattern(
                        new Vector2(ai.getX(), ai.getY()), length, horizontal, pauseInterval));
            }
        }
    }

    private static MapObject getObject(String objectName, MapLayer layer){
        for(MapObject object : layer.getObjects()){
            if(object.getName().equals(objectName)){
                return object;
            }
        }
        return null;
    }

    public static Collection<Emitter> createEmitters(TiledMap map){
        Collection<Emitter> emitters = new ArrayList<Emitter>();
        MapLayer emitterLayer = getLayer(ENEMY_EMITTER, map);
        if(emitterLayer != null){
            for (MapObject object : emitterLayer.getObjects()) {
                Emitter<Enemy> emitter = new Emitter<Enemy>(new Enemy.EnemyFactory(),
                        getNPCPositionX(object), getNPCPositionY(object));
                emitters.add(emitter);
            }
        }
        return emitters;
    }

    public static Collection<NPC> createNPCs(TiledMap map, World world){
        Collection<NPC> npcs = new ArrayList<NPC>();
        MapLayer npcLayer = getLayer(NPC_SPAWN, map);
        if(npcLayer != null){
            for (MapObject object : npcLayer.getObjects()) {
                NPC npc = new NPC(world, getNPCPositionX(object), getNPCPositionY(object));
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

    public static Collection<Checkpoint> createCheckpoints(TiledMap map, World world){
        Collection<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
        MapLayer npcLayer = getLayer(CHECKPOINT, map);
        if(npcLayer != null){
            for (MapObject object : npcLayer.getObjects()) {
                Checkpoint checkpoint = new Checkpoint(getNPCPositionX(object), getNPCPositionY(object), world, 5.0f);
                checkpoints.add(checkpoint);
            }
        }
        return checkpoints;
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

    public static Collection<CherryBlossom> createCherryBlossoms(TiledMap map, Level level, World world) {
        Collection<CherryBlossom> cherryBlossoms = new ArrayList<CherryBlossom>();
        for (MapObject cherryBlossomObj : getLayer(CHERRY_BLOSSOM, map).getObjects()) {
            cherryBlossoms.add(new CherryBlossom(getX(cherryBlossomObj), getY(cherryBlossomObj),
                    level.getWind(), ImageCache.cherryBlossom, world));
        }
        return cherryBlossoms;
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

    private static Float getFloatProperty(MapObject object, String propertyName) {
        return Float.valueOf(getStringProperty(object, propertyName));
    }

    private static Boolean getBooleanProperty(MapObject object, String propertyName) {
        return Boolean.valueOf(getStringProperty(object, propertyName));
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

    /**
     * To enforce the level designer to place all NPCs at the centre of their respective tiles:
     */
    private static float getNPCPositionX(MapObject object) {
        float npcPositionX = getX(object);
        //If not placed at tile centre:
        if(npcPositionX%1.0f != 0.5f){
            throw new IllegalNPCPositionException("NPC not placed at the centre of its tile: "+object);
        }
        return npcPositionX;
    }

    /**
     * To enforce the level designer to place all NPCs at the centre of their respective tiles:
     */
    private static float getNPCPositionY(MapObject object) {
        float npcPositionY = getY(object);
        //If not placed at tile centre:
        if(npcPositionY%1.0f != 0.5f){
            throw new IllegalNPCPositionException("NPC not placed at the centre of its tile: "+object);
        }
        return npcPositionY;
    }

}
