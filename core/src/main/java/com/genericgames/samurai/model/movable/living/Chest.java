package com.genericgames.samurai.model.movable.living;

import com.genericgames.samurai.model.Collidable;
import com.genericgames.samurai.model.WorldObject;

import java.util.List;

public class Chest extends Item implements Collidable {

    private List<Item> containedItems;

    public Chest(float posX, float posY){
        super(posX, posY);
    }

    public List<Item> getContainedItems(){
        return containedItems;
    }

}
