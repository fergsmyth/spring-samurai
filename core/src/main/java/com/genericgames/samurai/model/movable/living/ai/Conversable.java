package com.genericgames.samurai.model.movable.living.ai;

import com.genericgames.samurai.Dialogue;

public class Conversable extends AI {

    private Dialogue dialogue;

    public Dialogue getDialogue() {
        return dialogue;
    }

    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }

}
