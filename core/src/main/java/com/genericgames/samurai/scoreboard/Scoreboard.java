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

    public String[] getScoreNumber(){
        String[] scoreNumber = null;
        if (scores.size() <= 10){
            scoreNumber = new String[scores.size()];
            for (int i = 0; i < scores.size(); i++) {
                scoreNumber[i] = Integer.toString(scores.get(i).getScore());
            }
        }
        return scoreNumber;
    }

    public void addToScoreBoard(Score score){

        scores.add(score);
        Collections.sort(scores);
        if (scores.size() > 10){
            scores.remove(scores.size() - 1);
        }
    }

}
