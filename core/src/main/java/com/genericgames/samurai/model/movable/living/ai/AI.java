package com.genericgames.samurai.model.movable.living.ai;

import com.genericgames.samurai.ai.performers.AIActionPerformer;
import com.genericgames.samurai.ai.performers.AIActionPerformerProvider;
import com.genericgames.samurai.ai.routefinding.Route;
import com.genericgames.samurai.model.movable.living.Living;


public class AI extends Living {

    private AIActionPerformer actionPerformer;
    private Route route = new Route();
    private boolean playerAware = false;

    public AIActionPerformer getAIActionPerformer() {
        return actionPerformer;
    }

    public void setAIActionPerformer(AIActionPerformer actionPerformer) {
        this.actionPerformer = actionPerformer;
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


    public void damage(int damage){
        super.damage(damage);
        if(this.getHealth() > 0){
            setAIActionPerformer(AIActionPerformerProvider.getActionPerformer(ActionState.KNOCK_BACK, this));
        }
    }
}
