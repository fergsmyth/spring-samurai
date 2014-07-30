package com.genericgames.samurai.scoreboard;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Scoreboard implements Serializable{
    private List<Score> scores = new LinkedList<Score>();

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

    public boolean addToScoreBoard(String playerName, int levelNumber, int scoreCount){
        Score score = new Score(playerName, levelNumber, scoreCount);
        if (scores.size() < 10){
            scores.add(score);
            return true;
        }
        return false;
    }

}
