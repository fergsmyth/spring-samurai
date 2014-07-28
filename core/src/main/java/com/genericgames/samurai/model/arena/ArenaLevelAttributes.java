package com.genericgames.samurai.model.arena;

import com.genericgames.samurai.model.Emitter;

import java.util.List;

public class ArenaLevelAttributes {

    public static final int MAX_NUMBER_OF_ENEMIES_ON_SCREEN = 8;

    boolean arenaLevel = false;
    private int totalNumEnemiesKilled = 0;
    private Round round;

    private List<Emitter> enemyEmitters;

    public ArenaLevelAttributes(boolean arenaLevel) {
        this.arenaLevel = arenaLevel;
        this.round = new Round(0);
    }

    public void initiateNextRound(){
        round = new Round(round.getRoundNum()+1);
        initialiseEnemyEmitters();
        //TODO increase enemy skill level (speed, damage inflicted, health)
        //TODO increase number of enemies to be created
    }

    private void initialiseEnemyEmitters() {
        for(Emitter enemyEmitter : enemyEmitters){
            enemyEmitter.reinitialise();
            enemyEmitter.setEverLasting(true);
        }
    }

    public void incrementEnemiesKilledCounter(){
        totalNumEnemiesKilled++;
        round.incrementEnemiesKilledCounter();
    }

    public boolean isArenaLevel() {
        return arenaLevel;
    }

    public void setArenaLevel(boolean arenaLevel) {
        this.arenaLevel = arenaLevel;
    }

    public int getTotalNumEnemiesKilled() {
        return totalNumEnemiesKilled;
    }

    public void setTotalNumEnemiesKilled(int totalNumEnemiesKilled) {
        this.totalNumEnemiesKilled = totalNumEnemiesKilled;
    }

    public Round getRound() {
        return round;
    }

    public void updateArenaLevel(){
        if(round.allEnemiesDefeated()){
            if(round.handleCountdownToNextRound()){
                initiateNextRound();
            }
        }
    }

    public List<Emitter> getEnemyEmitters() {
        return enemyEmitters;
    }

    public void setEnemyEmitters(List<Emitter> enemyEmitters) {
        this.enemyEmitters = enemyEmitters;
    }

}
