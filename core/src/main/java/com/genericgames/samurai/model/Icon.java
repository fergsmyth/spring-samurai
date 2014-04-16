package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.genericgames.samurai.Dialogue;
import com.genericgames.samurai.utility.ImageCache;

public class Icon extends WorldObject {

    private Texture iconName;
    private String dialogue;
    private float scalingFactor;

    public Icon(float x, float y, Texture iconName, String dialogue, float scalingFactor){
        super(x,y);
        this.iconName = iconName;
        this.dialogue = dialogue;
    }

    public void setScalingFactor(float scalingFactor){
        this.scalingFactor = scalingFactor;
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
        batch.draw(ImageCache.conversationIcon, getX() - ImageCache.tileSize, getY(), 20, 20);
    }
}
