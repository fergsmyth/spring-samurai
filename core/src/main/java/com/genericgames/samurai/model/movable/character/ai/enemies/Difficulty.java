package com.genericgames.samurai.model.movable.character.ai.enemies;

public enum Difficulty {
    EASY, MEDIUM, DIFFICULT;

    public static Difficulty getMatchingDifficulty(String difficultyString) {
        if (difficultyString != null){
            String UpperCaseDifficultyString = difficultyString.toUpperCase();

            if (UpperCaseDifficultyString.equals(EASY.toString())) {
                return EASY;
            } else if (UpperCaseDifficultyString.equals(MEDIUM.toString())) {
                return MEDIUM;
            } else if (UpperCaseDifficultyString.equals(DIFFICULT.toString())) {
                return DIFFICULT;
            }
        }
        throw new IllegalArgumentException("No matching Difficulty found.");
    }
}
