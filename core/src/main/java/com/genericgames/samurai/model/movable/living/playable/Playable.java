package com.genericgames.samurai.model.movable.living.playable;

import java.util.ArrayList;
import java.util.Collection;

import com.genericgames.samurai.model.Player;
import com.genericgames.samurai.model.movable.living.Item;
import com.genericgames.samurai.model.movable.living.Living;
import com.genericgames.samurai.model.movable.living.Weapon;

/**
 * A character that can be controlled by the player
 */
public class Playable extends Living {

    Player controllingPlayer;
    Collection<Weapon> weapons = new ArrayList<Weapon>();
    Collection<Item> inventory = new ArrayList<Item>();

    Playable() {
        super();
        controllingPlayer = null;
    }

    public boolean isPlayerControlled(){
        return controllingPlayer != null;
    }
}
