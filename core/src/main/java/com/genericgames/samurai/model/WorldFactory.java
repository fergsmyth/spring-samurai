package com.genericgames.samurai.model;

import com.genericgames.samurai.model.movable.living.playable.PlayerCharacter;
import com.genericgames.samurai.physics.PhysicWorld;

public class WorldFactory {

    public static SamuraiWorld createSamuraiWorld(String levelFile, float x, float y){
        Level firstLevel = createLevel(levelFile, createCharacter(x,y));
        SamuraiWorld samuraiWorld = new SamuraiWorld(firstLevel);
        return samuraiWorld;
    }

    public static SamuraiWorld createSamuraiWorld(String levelFile){
        Level firstLevel = createLevelWithDefaultSpawn(levelFile);
        SamuraiWorld samuraiWorld = new SamuraiWorld(firstLevel);
        return samuraiWorld;
    }

    public static Level createLevelWithDefaultSpawn(String levelFile){
        Level level = createLevel(levelFile, createCharacter(0, 0));
        SpawnPoint spawnPoint = level.getSpawnPointByPosition(1);
        level.getPlayerCharacter().setPosition(spawnPoint.getX(), spawnPoint.getY());
        return level;
    }

    public static Level createLevel(String levelFile, PlayerCharacter character){
        Level level = new Level(levelFile, character);
        level.setPhysicalWorld(PhysicWorld.createPhysicWorld(level));
        return level;
    }

    public static PlayerCharacter createCharacter(float x, float y){
        PlayerCharacter character = new PlayerCharacter();
        character.setPosition(x, y);
        return character;
    }
}
