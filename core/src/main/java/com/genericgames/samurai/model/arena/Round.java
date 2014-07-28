package com.genericgames.samurai.model.arena;

public class Round {

    private int roundNum = 0;
    private int totalNumEnemiesForRound = 0;
    private int currentNumEnemies = 0;
    private int numEnemiesKilled = 0;

    private boolean countdownInitiated = false;
    private int numFramesUntilNextRound = 260;

    public Round(int roundNum){
        this.roundNum = roundNum;
        this.totalNumEnemiesForRound = roundNum;
    }

    public int getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(int roundNum) {
        this.roundNum = roundNum;
    }

    public int getNumEnemiesKilled() {
        return numEnemiesKilled;
    }

    public void setNumEnemiesKilled(int numEnemiesKilled) {
        this.numEnemiesKilled = numEnemiesKilled;
    }

    public void incrementEnemiesKilledCounter(){
        numEnemiesKilled++;
    }

    public void incrementCurrentNumEnemies(){
        currentNumEnemies++;
    }

    public boolean isCountdownInitiated() {
        return countdownInitiated;
    }

    public void setCountdownInitiated(boolean countdownInitiated) {
        this.countdownInitiated = countdownInitiated;
    }

    public int getNumFramesUntilNextRound() {
        return numFramesUntilNextRound;
    }

    public void setNumFramesUntilNextRound(int numFramesUntilNextRound) {
        this.numFramesUntilNextRound = numFramesUntilNextRound;
    }

    public int getTotalNumEnemiesForRound() {
        return totalNumEnemiesForRound;
    }

    public void setTotalNumEnemiesForRound(int totalNumEnemiesForRound) {
        this.totalNumEnemiesForRound = totalNumEnemiesForRound;
    }

    public int getCurrentNumEnemies() {
        return currentNumEnemies;
    }

    public void setCurrentNumEnemies(int currentNumEnemies) {
        this.currentNumEnemies = currentNumEnemies;
    }

    public boolean allEnemiesDefeated() {
        return numEnemiesKilled == totalNumEnemiesForRound;
    }

    /**
     * @return true if the new round can begin, otherwise false.
     */
    public boolean handleCountdownToNextRound() {
        setCountdownInitiated(true);
        numFramesUntilNextRound--;
        return getNumFramesUntilNextRound() <= 0;
    }

    public boolean canEnemyBeEmitted(){
        return this.getCurrentNumEnemies() < this.getTotalNumEnemiesForRound() &&
                this.getCurrentNumEnemies() < ArenaLevelAttributes.MAX_NUMBER_OF_ENEMIES_ON_SCREEN;
    }
}
