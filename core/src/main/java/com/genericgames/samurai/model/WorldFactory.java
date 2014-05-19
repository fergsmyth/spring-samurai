package com.genericgames.samurai.model;

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
        level.getPlayerCharacter().setPosition(spawnPoint.getX(), spawnPoint.getY());
        return level;
    }

}
