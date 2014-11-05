package com.genericgames.samurai.model.arena;

public class ArenaScore {

    private static final int FRAMES_PER_SEC = 60;

    private int score = 0;

    private int multiplier = 1;

    private int maxMultiplier = 10;
    private int timeLimitInSecs = 5;

    private int numFramesTillExpiry;

    public ArenaScore(){
        resetExpiryCountdown();
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getNumFramesTillExpiry() {
        return numFramesTillExpiry;
    }

    public void setNumFramesTillExpiry(int numFramesTillExpiry) {
        this.numFramesTillExpiry = numFramesTillExpiry;
    }

    public void resetExpiryCountdown() {
        numFramesTillExpiry = timeLimitInSecs*FRAMES_PER_SEC;
    }

    public void increaseScore(int numPoints){
        score = score + (numPoints*multiplier);
    }

    public void increaseMultiplier(){
        if(multiplier<maxMultiplier){
            multiplier++;
        }
    }

    public void tick(){
        if(numFramesTillExpiry <= 0){
            expireMultiplier();
        }
        numFramesTillExpiry--;
    }

    private void expireMultiplier() {
        multiplier = 1;
        resetExpiryCountdown();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
