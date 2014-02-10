package com.genericgames.samurai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Dialogue implements Iterator<String>, Iterable<String> {

    private Collection<String> phrases = new ArrayList<String>();
    private Iterator<String> iterator = phrases.iterator();

    public void addDialogue(String dialogue){
        this.phrases.add(dialogue);
    }

    public Collection<String> getPhrases(){
        return phrases;
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public String next() {
        return iterator.next();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
