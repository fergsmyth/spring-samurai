package com.example.mylibgdxgame.model.movable.living.playable;

import com.example.mylibgdxgame.model.movable.living.Item;
import com.example.mylibgdxgame.model.Player;
import com.example.mylibgdxgame.model.movable.living.Living;
import com.example.mylibgdxgame.model.movable.living.Weapon;

import java.util.ArrayList;
import java.util.Collection;

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

    Playable(Player controllingPlayer) {
        super();
        this.controllingPlayer = controllingPlayer;
    }

    Playable(Player controllingPlayer, int maxHealth) {
        super();
        this.controllingPlayer = controllingPlayer;
    }

    Playable(Player controllingPlayer, int positonX, int positonY, int direction) {
        super(positonX, positonY, direction);
        this.controllingPlayer = controllingPlayer;
    }

    Playable(Player controllingPlayer, int maxHealth, int positonX, int positonY, int direction) {
        super(positonX, positonY, direction);
        this.controllingPlayer = controllingPlayer;
    }

    Playable(Player controllingPlayer, int positonX, int positonY, int direction, int velocityX, int velocityY) {
        super(positonX, positonY, direction, velocityX, velocityY);
        this.controllingPlayer = controllingPlayer;
    }

    Playable(Player controllingPlayer, int maxHealth, int positonX, int positonY, int direction, int velocityX, int velocityY) {
        super(positonX, positonY, direction, velocityX, velocityY);
        this.controllingPlayer = controllingPlayer;
    }


    public boolean isPlayerControlled(){
        return controllingPlayer != null;
    }
}
