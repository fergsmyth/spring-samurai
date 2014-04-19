package com.genericgames.samurai.model;

import com.genericgames.samurai.physics.PhysicWorld;

public class WorldFactory {

    /**
     * Used when to
     */
    public static SamuraiWorld loadSamuraiWorld(String levelFile, float x, float y){
        Level firstLevel = new Level(levelFile, x, y, false);
        SamuraiWorld samuraiWorld = new SamuraiWorld(firstLevel);
        //firstLevel.setPhysicsWorld(PhysicWorld.createPhysicWorld(firstLevel));
        return samuraiWorld;
    }

    public static SamuraiWorld createSamuraiWorld(String levelFile){
        Level firstLevel = createLevelWithSpawnPos(levelFile, 0, 0, 1);
        SamuraiWorld samuraiWorld = new SamuraiWorld(firstLevel);
        return samuraiWorld;
    }

    public static Level createLevelWithSpawnPos(String levelFile, int x, int y, int doorNumber){
        Level level = new Level(levelFile, 0, 0, true);
        SpawnPoint spawnPoint = level.getDoorPosition(doorNumber);
        level.getPlayerCharacter().setPosition(spawnPoint.getX(), spawnPoint.getY());
        //level.setPhysicsWorld(PhysicWorld.createPhysicWorld(level));
        return level;
    }

}
