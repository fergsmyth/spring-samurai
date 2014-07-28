package com.genericgames.samurai.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.ai.AIHelper;
import com.genericgames.samurai.model.arena.ArenaLevelAttributes;
import com.genericgames.samurai.model.arena.Round;
import com.genericgames.samurai.model.movable.character.WorldCharacter;
import com.genericgames.samurai.model.movable.character.ai.Enemy;
import com.genericgames.samurai.model.movable.character.ai.NPC;
import com.genericgames.samurai.model.state.living.Living;
import com.genericgames.samurai.model.weapon.Quiver;
import com.genericgames.samurai.model.weather.WeatherHelper;
import com.genericgames.samurai.physics.Arrow;
import com.genericgames.samurai.physics.PhysicalWorldHelper;

public class SamuraiWorld {

    private Level currentLevel;

    public SamuraiWorld(Level firstLevel) {
        currentLevel = firstLevel;
    }

    public Level getCurrentLevel(){
        return currentLevel;
    }

    public void setCurrentLevel(Level level){
        currentLevel = level;
    }

    public String getCurrentLevelFile(){
        return currentLevel.getLevelFile();
    }

    public PlayerCharacter getPlayerCharacter() {
        return currentLevel.getPlayerCharacter();
    }

    public void addArrow(Arrow arrow){ currentLevel.addArrow(arrow); }

    public void removeArrow(Arrow removeArrow){ currentLevel.removeArrow(removeArrow);}

    public Collection<Arrow> getArrows() {
        return currentLevel.getArrows();
    }

    public Collection<Wall> getWalls() {
        return currentLevel.getWalls();
    }

    public Collection<ImpassableGate> getImpassableGates() {
        return currentLevel.getImpassableGates();
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

    public Collection<Enemy> getEnemies(){
        return currentLevel.getEnemies();
    }

    public Collection<Emitter> getEmitters(){
        return currentLevel.getEmitters();
    }

    public Collection<NPC> getNPCs(){
        return currentLevel.getNPCs();
    }

    public Collection<CherryBlossom> getCherryBlossoms() {
        return currentLevel.getCherryBlossoms();
    }

    public Collection<Quiver> getQuivers() {
        return currentLevel.getQuivers();
    }

    public Collection<Particle> getParticles() {
        return currentLevel.getParticles();
    }

    public List<WorldCharacter> getAllCharacters() {
        List<WorldCharacter> allCharacters = new ArrayList<WorldCharacter>();
        allCharacters.addAll(getEnemies());
        allCharacters.add(getPlayerCharacter());
        allCharacters.addAll(getNPCs());
        return allCharacters;
    }

    public SpawnPoint getSpawnPointByPosition(int position){
        return currentLevel.getDoorPosition(position);
    }

    public int getLevelWidth() {
        return currentLevel.getLevelWidth();
    }

    public int getLevelHeight() {
        return currentLevel.getLevelHeight();
    }

    public World getPhysicalWorld() {
        return currentLevel.getPhysicsWorld();
    }

    public void setPhysicalWorld(World physicalWorld){
        this.currentLevel.setPhysicsWorld(physicalWorld);
    }

    public void addObjectToDelete(WorldObject worldObject){
        this.currentLevel.addObjectToDelete(worldObject);
    }

    public void deleteWorldObjects(){
        this.currentLevel.deleteWorldObjects();
    }

    public void handleEmitters() {
        handleEnemyEmitters();

        for(CherryBlossom cherryBlossom : getCherryBlossoms()){
            for(Emitter emitter : cherryBlossom.getPetalEmitters()){
                emitter.iterate(this);
            }
        }

        if(this.getCurrentLevel().getWeatherEmitter() != null){
            this.getCurrentLevel().getWeatherEmitter().iterate(this);
        }
    }

    private void handleEnemyEmitters() {
        ArenaLevelAttributes arenaLevelAttributes = currentLevel.getArenaLevelAttributes();
        if(arenaLevelAttributes.isArenaLevel()){
            handleArenaLevelEnemyEmitters(arenaLevelAttributes);
        }
        else {
            for(Emitter emitter : getEmitters()){
                emitter.iterate(this);
            }
        }
    }

    private void handleArenaLevelEnemyEmitters(ArenaLevelAttributes arenaLevelAttributes) {
        Round round = arenaLevelAttributes.getRound();
        List<Emitter> arenaEnemyEmitters = arenaLevelAttributes.getEnemyEmitters();
        boolean shuffleEmitters = false;
        for(Emitter enemyEmitter : arenaEnemyEmitters){
            if(round.canEnemyBeEmitted()){
                boolean enemyWasEmitted = enemyEmitter.iterate(this);
                if(enemyWasEmitted){
                    round.incrementCurrentNumEnemies();
                    shuffleEmitters = true;
                }
            }
        }

        if(shuffleEmitters){
            Collections.shuffle(arenaEnemyEmitters);
        }
    }

    /**
     * Handles all iterative per-frame-processing, that's not rendering-related
     */
    public void step() {
        PhysicalWorldHelper.checkForCollisions(this);
        updateLevel();
        moveNonPhysicalWorldObjects();
        PhysicalWorldHelper.handleEnemyAI(this);
        AIHelper.handlePlayerHealthRegen(this);
        handleInvincibilityPeriods();
        handleEmitters();
        deleteWorldObjects();
    }

    private void updateLevel() {
        ArenaLevelAttributes arenaLevelAttributes = currentLevel.getArenaLevelAttributes();
        if(arenaLevelAttributes.isArenaLevel()){
            arenaLevelAttributes.updateArenaLevel();
        }
    }

    /**
     * Handles movement of all world objects that have no physical properties.
     */
    private void moveNonPhysicalWorldObjects() {
        for(Particle particle : getParticles()){
            particle.update(this);
        }

        //Update Weather Emitter position
        RandomSpaceEmitter weatherEmitter = getCurrentLevel().getWeatherEmitter();
        if(weatherEmitter != null){
            Vector2 weatherEmitterPosition = WeatherHelper.getWeatherEmitterPosition(currentLevel);
            weatherEmitter.setRandomSpacePosition(
                weatherEmitterPosition.x, weatherEmitterPosition.y);
        }
    }

    private void handleInvincibilityPeriods() {
        for(Living livingCharacter : getAllLivingCharacters()){
            livingCharacter.incrementInvisibility();
        }
    }

    private Collection<Living> getAllLivingCharacters() {
        Collection<Living> livingCharacters = new ArrayList<Living>();
        for(WorldCharacter character : getAllCharacters()){
            if(character instanceof Living){
                livingCharacters.add((Living)character);
            }
        }
        return livingCharacters;
    }
}
