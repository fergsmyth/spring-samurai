package com.genericgames.samurai.model.movable.living.ai;

import com.genericgames.samurai.ai.routefinding.Route;
import com.genericgames.samurai.model.movable.living.Living;


public class AI extends Living {

    private Route route;
    private boolean playerAware = false;

    public AI(){
        route = new Route();
    }

    public boolean isPlayerAware() {
        return playerAware;
    }

    public void setPlayerAware(boolean playerAware) {
        this.playerAware = playerAware;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
