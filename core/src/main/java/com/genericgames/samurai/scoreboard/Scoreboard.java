package com.genericgames.samurai.scoreboard;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Scoreboard implements Serializable{
    private List<Score> scores = new LinkedList<Score>();

    public List<Score> getScores(){
        return scores;
    }

    public void addToScoreBoard(Score score){
        scores.add(score);
        Collections.sort(scores);
        if (scores.size() > 10){
            scores.remove(scores.size() - 1);
        }
    }

}
