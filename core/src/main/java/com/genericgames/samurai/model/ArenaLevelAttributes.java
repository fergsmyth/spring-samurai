package com.genericgames.samurai.model;

public class ArenaLevelAttributes {

    boolean arenaLevel = false;
    private int numEnemiesKilled = 10;
    private int round = 0;

    public ArenaLevelAttributes(boolean arenaLevel) {
        this.arenaLevel = arenaLevel;
    }

    public void initiateNextRound(){
        round++;
        //TODO increase enemy skill level (speed, damage inflicted, health)
        //TODO increase number of enemies to be created
    }

    public void incrementEnemiesKilledCounter(){
        numEnemiesKilled++;
    }

    public boolean isArenaLevel() {
        return arenaLevel;
    }

    public void setArenaLevel(boolean arenaLevel) {
        this.arenaLevel = arenaLevel;
    }

    public int getNumEnemiesKilled() {
        return numEnemiesKilled;
    }

    public void setNumEnemiesKilled(int numEnemiesKilled) {
        this.numEnemiesKilled = numEnemiesKilled;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
