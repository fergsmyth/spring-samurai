package com.genericgames.samurai.scoreboard;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Score implements Serializable, Comparable<Score> {
    private String playerName;
    private int levelNumber;
    private long timeTaken;
    private int score;

    public Score(String playerName, int levelNumber, int score, long timeTaken){
        this.levelNumber = levelNumber;
        this.playerName = playerName;
        this.timeTaken = timeTaken;
        this.score = score;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public String getPlayerName() {
        return playerName;
    }

    public long getTimeTaken(){
        return timeTaken;
    }

    public String getTimeTakenStringFormat(){
        Date date = new Date(timeTaken);
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        return formatter.format(date);
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
