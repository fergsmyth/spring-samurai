package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.Texture;

public class Icon extends WorldObject {

    private Texture iconName;

    public Icon(float x, float y, Texture iconName){
        super(x,y);
        this.iconName = iconName;
    }

    public Texture getIconName() {
        return iconName;
    }

    public void setIconName(Texture iconName) {
        this.iconName = iconName;
    }
}
