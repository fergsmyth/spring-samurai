package com.genericgames.samurai.model.movable.character.ai;

public abstract class Conversable extends AI {

    private String dialogue;

    public Conversable(float x, float y) {
        super(x, y);
    }

    public String getDialogue() {
        return dialogue;
    }

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

}
