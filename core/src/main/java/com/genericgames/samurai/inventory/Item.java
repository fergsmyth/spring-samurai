package com.genericgames.samurai.inventory;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.genericgames.samurai.model.WorldObject;

public class Item extends WorldObject {

    private String imageName;

    public Item(String imageName){
        this.imageName = imageName;
    }

    public String getImageName(){
        return imageName;
    }

}
