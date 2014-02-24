package com.genericgames.samurai;

public class Phrase {

    private String character;
    private String phrase;

    public Phrase(String character, String phrase){
        this.character = character;
        this.phrase = phrase;
    }

    public String getCharacter(){
        return character;
    }

    public String getPhrase(){
        return phrase;
    }
}
