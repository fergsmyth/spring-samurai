package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.Texture;

public class DialogueIcon extends Icon{
    private String dialogue;

    public DialogueIcon(float x, float y, Texture iconName, String dialogue, float scalingFactor) {
        super(x, y, iconName, scalingFactor);
        this.dialogue = dialogue;
    }

    public String getDialogue(){
        return dialogue;
    }

}
