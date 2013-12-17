package com.genericgames.samurai.model;

import com.genericgames.samurai.model.movable.living.playable.PlayerCharacter;
import com.genericgames.samurai.physics.PhysicWorld;

public class WorldFactory {

    public static SamuraiWorld createSamuraiWorld(String levelFile, float x, float y){
        Level firstLevel = createLevel(levelFile, createCharacter(x,y));
        SamuraiWorld samuraiWorld = new SamuraiWorld(firstLevel);
        firstLevel.setPhysicalWorld(PhysicWorld.createPhysicWorld(firstLevel));
        return samuraiWorld;
    }

    public static SamuraiWorld createSamuraiWorld(String levelFile){
        Level firstLevel = createLevelWithDefaultSpawnPos(levelFile);
        SamuraiWorld samuraiWorld = new SamuraiWorld(firstLevel);
        return samuraiWorld;
    }

    public static Level createLevelWithDefaultSpawnPos(String levelFile){
        return createLevelWithSpawnPos(levelFile, createCharacter(0, 0), 1);
    }

    public static Level createLevelWithSpawnPos(String levelFile, PlayerCharacter character, int spawnNumber){
        Level level = createLevel(levelFile, character);
        SpawnPoint spawnPoint = level.getSpawnPointByPosition(spawnNumber);
        character.setPosition(spawnPoint.getX(), spawnPoint.getY());
        level.setPhysicalWorld(PhysicWorld.createPhysicWorld(level));
        return level;
    }

    public static Level createLevel(String levelFile, PlayerCharacter character){
        return new Level(levelFile, character);
    }

    public static PlayerCharacter createCharacter(float x, float y){
        PlayerCharacter character = new PlayerCharacter();
        character.setPosition(x, y);
        return character;
    }
}
