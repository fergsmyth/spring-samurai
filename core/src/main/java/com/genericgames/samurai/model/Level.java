package com.genericgames.samurai.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.ai.routefinding.MapNode;
import com.genericgames.samurai.ai.routefinding.RouteCostMap;
import com.genericgames.samurai.ai.routefinding.RouteFindingHelper;
import com.genericgames.samurai.audio.SoundEffectCache;
import com.genericgames.samurai.map.LevelFactory;
import com.genericgames.samurai.model.arena.ArenaLevelAttributes;
import com.genericgames.samurai.model.movable.character.ai.enemies.Enemy;
import com.genericgames.samurai.model.movable.character.ai.NPC;
import com.genericgames.samurai.model.timeinterval.FixedTimeInterval;
import com.genericgames.samurai.model.weapon.Quiver;
import com.genericgames.samurai.model.weather.Weather;
import com.genericgames.samurai.model.weather.WeatherProvider;
import com.genericgames.samurai.model.weather.Wind;
import com.genericgames.samurai.physics.Arrow;
import com.genericgames.samurai.utility.ImageCache;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Level implements Serializable {
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String ARENA = "arena";
    public static final String WEATHER = "weather";
    private String levelFile;
    private int levelHeight;
    private int levelWidth;

    private ArenaLevelAttributes arenaLevelAttributes;
    private Wind wind;
    private RandomSpaceEmitter weatherEmitter;
    private Emitter bloodSplatterEmitter;

    private PlayerCharacter playerCharacter;
    private World physicsWorld;

    private RouteCostMap routingFindingRouteCostMap;

    private Collection<Arrow> arrows;
    private Collection<Chest> chests;
    private Collection<Door> doors;
    private Collection<Enemy> enemies;
    private Collection<Emitter> emitters;
    private Collection<NPC> npcs;
    private Collection<SpawnPoint> spawnPoints;
    private Collection<Checkpoint> checkpoints;
    private Collection<Roof> roofTiles;
    private Collection<Wall> walls;
    private Collection<ImpassableGate> gates;
    private Collection<CherryBlossom> cherryBlossoms;
    private Collection<Quiver> quivers;
    private Collection<Particle> particles;
    private Collection<WorldObject> objectsToDelete;

    public Level(String file, float playerX, float playerY, boolean needsSpawnPoint){
        levelFile = file;
        physicsWorld = new World(new Vector2(0, 0), true);
        TiledMap map = new TmxMapLoader().load(levelFile);
        setLevelDimensions(map);
        initiateArenaAttributes(map);
        initiateWind(map);
        ImageCache.load();
        SoundEffectCache.load();

        doors = LevelFactory.createDoors(map, physicsWorld);
        walls = LevelFactory.createWalls(map, physicsWorld);
        gates = LevelFactory.createImpassableGates(map, physicsWorld);
        spawnPoints = LevelFactory.createPlayerSpawns(map);
        if (needsSpawnPoint){
            SpawnPoint point = getDoorPosition(1);
            playerX = point.getX();
            playerY = point.getY();
        }
        playerCharacter = LevelFactory.createPlayer(playerX, playerY, physicsWorld);
        chests = new ArrayList<Chest>();
        npcs = LevelFactory.createNPCs(map, physicsWorld);
        checkpoints = LevelFactory.createCheckpoints(map, physicsWorld);
        enemies = LevelFactory.createEnemies(map, physicsWorld);
        if(arenaLevelAttributes.isArenaLevel()){
            //Set enemy emitters on arena attributes, not level
            arenaLevelAttributes.setEnemyEmitters(LevelFactory.createEnemyEmitters(map));
            arenaLevelAttributes.setQuiverEmitters(LevelFactory.createQuiverEmitters(map));
        }
        else {
            //Set enemy emitters on level
            emitters = LevelFactory.createEnemyEmitters(map);
        }
        cherryBlossoms = LevelFactory.createCherryBlossoms(map, this, physicsWorld);
        quivers = LevelFactory.createQuivers(map, physicsWorld);
        particles = new ArrayList<Particle>();
        roofTiles = new ArrayList<Roof>();
        arrows = new ArrayList<Arrow>();
        objectsToDelete = new ArrayList<WorldObject>();
        initiateWeather(map);
        bloodSplatterEmitter = new Emitter(new BloodParticle.BloodParticleFactory(),
                0, 0, new Vector2(0.03f, 0.03f), 90, true, new FixedTimeInterval(1), 10);

        loadLevel();
    }

    private void initiateArenaAttributes(TiledMap map) {
        this.arenaLevelAttributes = new ArenaLevelAttributes(Boolean.valueOf(
                map.getProperties().get(ARENA, String.class)));
    }

    private void initiateWind(TiledMap map) {
        //TODO Load wind from level map?
        this.wind = new Wind(new Vector2(-1, -1), 0.1f);
    }

    private void initiateWeather(TiledMap map) {
        weatherEmitter = WeatherProvider.getWeatherEmitter(
                Weather.valueOf(map.getProperties().get(WEATHER, String.class)), this);
    }

    public void loadLevel(){
        //LevelLoader.getInstance().loadLevel(this);
        routingFindingRouteCostMap = new RouteCostMap(levelWidth, levelHeight);
        loadRouteFindingMap();
    }

    private void setLevelDimensions(TiledMap map){
        MapProperties properties = map.getProperties();
        levelWidth = properties.get(WIDTH, Integer.class);
        levelHeight = properties.get(HEIGHT, Integer.class);
    }

    private void loadRouteFindingMap() {
        //For each collidable object in the level:
        for(MapNode mapNode :routingFindingRouteCostMap.getNodes()){
            for(Chest chest : this.getChests()){
                if(mapNode.getPositionX() == chest.getX() && mapNode.getPositionY() == chest.getY()){
                    mapNode.setCost(RouteFindingHelper.getCollidableNodeCost());
                    break;
                }
            }
            for(Door door : this.getDoors()){
                if(mapNode.getPositionX() == door.getX() && mapNode.getPositionY() == door.getY()){
                    mapNode.setCost(RouteFindingHelper.getCollidableNodeCost());
                    break;
                }
            }
            for(Wall wall : this.getWalls()){
                if(mapNode.getPositionX() == wall.getX() && mapNode.getPositionY() == wall.getY()){
                    mapNode.setCost(RouteFindingHelper.getCollidableNodeCost());
                    break;
                }
            }
        }
    }

    public String getLevelFile(){
        return levelFile;
    }

    public PlayerCharacter getPlayerCharacter(){
        return playerCharacter;
    }

    public void addArrow(Arrow arrow){ this.arrows.add(arrow); }

    public void removeArrow(Arrow removeArrow){ this.arrows.remove(removeArrow);}

    public void addDoors(Collection<Door> doors){
        this.doors.addAll(doors);
    }

    public void addNPCs(Collection<NPC> npcs){
        this.npcs.addAll(npcs);
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

    public Collection<NPC> getNPCs() {
        return npcs;
    }

    public Collection<Arrow> getArrows() {
        return arrows;
    }

    public Collection<Door> getDoors() {
        return doors;
    }

    public Collection<Wall> getWalls() {
        return walls;
    }

    public Collection<ImpassableGate> getImpassableGates() {
        return gates;
    }

    public Collection<Chest> getChests() {
        return chests;
    }

    public Collection<Enemy> getEnemies() {
        return enemies;
    }

    public Collection<Emitter> getEmitters() {
        return emitters;
    }

    public Collection<Roof> getRoofTiles() {
        return roofTiles;
    }

    public SpawnPoint getDoorPosition(int position){
            for(SpawnPoint point : spawnPoints){
                //System.out.println("Spawn Positions :" + point.getSpawnNumber());
                if (point.getSpawnNumber() == position){
                    return point;
                }
        }
        //TODO Make this an exception
        Gdx.app.log("Level", "No spawn found for position " +  position);
        return new SpawnPoint(1, 1, 0);
    }

    public World getPhysicsWorld() {
        return physicsWorld;
    }

    public void setPhysicsWorld(World physicsWorld){
        this.physicsWorld = physicsWorld;
    }

    private void readObject(ObjectInputStream stream)
            throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    private Object writeReplace() {
        return new LevelProxy(this);
    }

    public void addObjectToDelete(WorldObject objectToDelete){
        objectsToDelete.add(objectToDelete);
    }

    public void deleteWorldObjects(){
        for(WorldObject objectToDelete : objectsToDelete){
            objectToDelete.deleteBody(physicsWorld);
            removeObjectReference(objectToDelete);
        }
        objectsToDelete.clear();
    }

    private void removeObjectReference(WorldObject objectToRemove){
        if (objectToRemove instanceof Arrow){
            arrows.remove(objectToRemove);
        } else if (objectToRemove instanceof Particle){
            particles.remove(objectToRemove);
        } else if (objectToRemove instanceof Checkpoint){
            checkpoints.remove(objectToRemove);
        }
        else if (objectToRemove instanceof Quiver){
            quivers.remove(objectToRemove);
        }
    }

    public ArenaLevelAttributes getArenaLevelAttributes() {
        return arenaLevelAttributes;
    }

    public void setArenaLevelAttributes(ArenaLevelAttributes arenaLevelAttributes) {
        this.arenaLevelAttributes = arenaLevelAttributes;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Collection<Particle> getParticles() {
        return particles;
    }

    public Collection<CherryBlossom> getCherryBlossoms() {
        return cherryBlossoms;
    }

    public Collection<Quiver> getQuivers() {
        return quivers;
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
            return new Level(levelFile, playerX, playerY, false);
        }

    }

    public RouteCostMap getRoutingFindingRouteCostMap() {
        return routingFindingRouteCostMap;
    }

    public RandomSpaceEmitter getWeatherEmitter() {
        return weatherEmitter;
    }

    public void setWeatherEmitter(RandomSpaceEmitter weatherEmitter) {
        this.weatherEmitter = weatherEmitter;
    }

    public Emitter getBloodSplatterEmitter() {
        return bloodSplatterEmitter;
    }

    public void setBloodSplatterEmitter(Emitter bloodSplatterEmitter) {
        this.bloodSplatterEmitter = bloodSplatterEmitter;
    }

}
