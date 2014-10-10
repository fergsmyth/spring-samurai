package com.genericgames.samurai.model.arena;

import com.genericgames.samurai.model.Emitter;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.character.ai.enemies.DifficultEnemy;
import com.genericgames.samurai.model.movable.character.ai.enemies.EasyEnemy;
import com.genericgames.samurai.model.movable.character.ai.enemies.MediumEnemy;

import java.util.Collections;
import java.util.List;

public class ArenaLevelAttributes {

    public static final int MAX_NUMBER_OF_ENEMIES_ON_SCREEN = 8;
    public static final int MEDIUM_DIFFICULTY_THRESHOLD = 5;
    public static final int DIFFICULT_DIFFICULTY_THRESHOLD = 10;

    boolean arenaLevel = false;
    private int totalNumEnemiesKilled = 0;
    private Round round;
    long startTime = 0;

    private List<Emitter> enemyEmitters;
    private List<Emitter> quiverEmitters;

    public ArenaLevelAttributes(boolean arenaLevel) {
        this.arenaLevel = arenaLevel;
        this.round = new Round(0);
    }

    public void initiateNextRound(SamuraiWorld samuraiWorld){
        round = new Round(round.getRoundNum()+1);
        initialiseEnemyEmitters();
        emitQuiver(samuraiWorld);
    }

    private void emitQuiver(SamuraiWorld samuraiWorld) {
        if(round.getRoundNum()%2 == 0){
            for(Emitter emitter : quiverEmitters){
                emitter.emit(samuraiWorld);
            }
        }
    }

    private void initialiseEnemyEmitters() {
        int numberOfMediumEmitters = getNumMediumEmitters();
        int numberOfDifficultEmitters = getNumDifficultEmitters();

        Collections.shuffle(enemyEmitters);
        for(Emitter enemyEmitter : enemyEmitters){
            if (numberOfDifficultEmitters > 0){
                enemyEmitter.setFactory(new DifficultEnemy.DifficultEnemyFactory());
                numberOfDifficultEmitters--;
            }
            else if(numberOfMediumEmitters > 0){
                enemyEmitter.setFactory(new MediumEnemy.MediumEnemyFactory());
                numberOfMediumEmitters--;
            }
            else {
                enemyEmitter.setFactory(new EasyEnemy.EasyEnemyFactory());
            }

            enemyEmitter.reinitialise();
            enemyEmitter.setEverLasting(true);
        }
    }

    private int getNumDifficultEmitters() {
        int numRoundsPastThreshold = round.getRoundNum() - DIFFICULT_DIFFICULTY_THRESHOLD;
        return numRoundsPastThreshold < 0 ? 0 : numRoundsPastThreshold;
    }

    private int getNumMediumEmitters() {
        int numRoundsPastThreshold = round.getRoundNum() - MEDIUM_DIFFICULTY_THRESHOLD;
        return numRoundsPastThreshold < 0 ? 0 : numRoundsPastThreshold;
    }

    public void incrementEnemiesKilledCounter(){
        totalNumEnemiesKilled++;
        round.incrementEnemiesKilledCounter();
    }

    public boolean isArenaLevel() {
        return arenaLevel;
    }

    public long getStartTime(){
        return startTime;
    }

    public void setStartTime(long startTime){
        this.startTime= startTime;
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

    public void updateArenaLevel(SamuraiWorld samuraiWorld){
        if(round.allEnemiesDefeated()){
            if(round.handleCountdownToNextRound()){
                initiateNextRound(samuraiWorld);
            }
        }
    }

    public List<Emitter> getEnemyEmitters() {
        return enemyEmitters;
    }

    public void setEnemyEmitters(List<Emitter> enemyEmitters) {
        this.enemyEmitters = enemyEmitters;
    }

    public List<Emitter> getQuiverEmitters() {
        return quiverEmitters;
    }

    public void setQuiverEmitters(List<Emitter> quiverEmitters) {
        this.quiverEmitters = quiverEmitters;
    }
}
