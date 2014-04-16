package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.Texture;
import com.genericgames.samurai.utility.ImageCache;

public class DialogueIcon extends Icon{
    private String dialogue;

    public DialogueIcon(float x, float y, Texture iconName, String dialogue, float scalingFactor) {
        super(x - ImageCache.tileSize, y, iconName, scalingFactor);
        this.dialogue = dialogue;
    }

    public String getDialogue(){
        return dialogue;
    }

}
