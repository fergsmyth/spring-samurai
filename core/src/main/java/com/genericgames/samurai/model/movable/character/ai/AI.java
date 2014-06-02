package com.genericgames.samurai.model.movable.character.ai;

import com.genericgames.samurai.ai.patrolpattern.PatrolPatternGroup;
import com.genericgames.samurai.ai.performers.AIActionPerformer;
import com.genericgames.samurai.ai.routefinding.Route;
import com.genericgames.samurai.model.movable.character.WorldCharacter;

public abstract class AI extends WorldCharacter {

    private AIActionPerformer actionPerformer;

    private PatrolPatternGroup patrolPatternGroup = new PatrolPatternGroup();
    private Route route = new Route();
    private boolean playerAware = false;

    public AI(float x, float y){
        super(x, y);
	}

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

    public PatrolPatternGroup getPatrolPatternGroup() {
        return patrolPatternGroup;
    }
}
