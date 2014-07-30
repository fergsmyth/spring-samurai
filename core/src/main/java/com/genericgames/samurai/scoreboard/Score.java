package com.genericgames.samurai.scoreboard;

import java.io.Serializable;

public class Score implements Serializable {
    private String playerName;
    private int levelNumber;
    private int score;

    public Score(String playerName, int levelNumber, int score){
        this.playerName = playerName;
        this.levelNumber = levelNumber;
        this.score = score;
    }

    public int getScore(){
        return score;
    }
}
