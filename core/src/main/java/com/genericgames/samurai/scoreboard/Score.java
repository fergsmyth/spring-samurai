package com.genericgames.samurai.scoreboard;

import java.io.Serializable;

public class Score implements Serializable, Comparable<Score> {
    private String playerName;
    private int levelNumber;
    private int score;

    public Score(String playerName, int levelNumber, int score){
        this.playerName = playerName;
        this.levelNumber = levelNumber;
        this.score = score;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore(){
        return score;
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    @Override
    public int compareTo(Score o) {
        return Integer.valueOf(o.getScore()).compareTo(score);
    }
}
