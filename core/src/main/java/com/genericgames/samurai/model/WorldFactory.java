package com.genericgames.samurai.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.genericgames.samurai.physics.PhysicalWorldHelper;

public class WorldFactory {

    /**
     * Used when to
     */
    public static SamuraiWorld loadSamuraiWorld(String levelFile, float x, float y){
        Level firstLevel = new Level(levelFile, x, y, false);
        return new SamuraiWorld(firstLevel);
    }

    public static SamuraiWorld createSamuraiWorld(String levelFile){
        Level firstLevel = createLevelWithSpawnPos(levelFile, 0, 0, 1);
        return new SamuraiWorld(firstLevel);
    }

    public static Level createLevelWithSpawnPos(String levelFile, int x, int y, int doorNumber){
        Level level = new Level(levelFile, x, y, true);
        SpawnPoint spawnPoint = level.getDoorPosition(doorNumber);
        PlayerCharacter playerCharacter = level.getPlayerCharacter();
        Body playerBody = PhysicalWorldHelper.getBodyFor(playerCharacter, level.getPhysicsWorld());
        playerBody.setTransform(spawnPoint.getX(), spawnPoint.getY(), playerCharacter.getRotation());
        playerCharacter.setPosition(spawnPoint.getX(), spawnPoint.getY());
        return level;
    }

}
