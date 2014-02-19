package com.genericgames.samurai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Dialogue {

    private Collection<String> phrases = new ArrayList<String>();

    public void addDialogue(String dialogue){
        this.phrases.add(dialogue);
    }

    public Collection<String> getPhrases(){
        return phrases;
    }

}
