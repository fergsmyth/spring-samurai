package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.genericgames.samurai.Dialogue;

public class Icon extends WorldObject {

    private Texture iconName;
    private String dialogue;

    public Icon(float x, float y, Texture iconName, String dialogue){
        super(x,y);
        this.iconName = iconName;
        this.dialogue = dialogue;
    }

    public Texture getIconName() {
        return iconName;
    }

    public String getDialogue(){
        return dialogue;
    }

    public void setIconName(Texture iconName) {
        this.iconName = iconName;
    }

    @Override
    public void draw(SpriteBatch batch) {

    }
}
