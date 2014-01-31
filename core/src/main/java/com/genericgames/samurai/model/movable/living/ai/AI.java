package com.genericgames.samurai.model.movable.living.ai;

import com.genericgames.samurai.model.movable.living.Living;

public class AI extends Living {

    private boolean playerAware = false;

    public boolean isPlayerAware() {
        return playerAware;
    }

    public void setPlayerAware(boolean playerAware) {
        this.playerAware = playerAware;
    }
}
