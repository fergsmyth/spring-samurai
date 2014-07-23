package com.genericgames.samurai.model.weapon;

import com.genericgames.samurai.model.Icon;
import com.genericgames.samurai.screens.GameView;

public enum Weapon {
    SWORD, BOW;

    public Icon getIcon(GameView gameView){
        switch (this){
            case SWORD:
                return gameView.getSwordIcon();
            case BOW:
                return gameView.getBowIcon();
            default:
                return null;
        }
    }
}
