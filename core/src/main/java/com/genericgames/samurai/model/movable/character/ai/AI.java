package com.genericgames.samurai.model.movable.character.ai;

import com.genericgames.samurai.ai.performers.AIActionPerformer;
import com.genericgames.samurai.ai.routefinding.Route;
import com.genericgames.samurai.model.movable.character.WorldCharacter;
import com.genericgames.samurai.model.state.StatefulImpl;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.state.Stateful;

public class AI extends WorldCharacter implements Stateful {

    private AIActionPerformer actionPerformer;
    private Route route = new Route();
    private boolean playerAware = false;

    private Stateful stateful;

    public AI(){
        super();
        stateful = new StatefulImpl();
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

    public State getState() {
        return stateful.getState();
    }

    public void setState(State state) {
        stateful.setState(state);
    }

    public float getStateTime() {
        return stateful.getStateTime();
    }

    public void setStateTime(float stateTime) {
        stateful.setStateTime(stateTime);
    }

    public void incrementStateTime() {
        stateful.incrementStateTime();
    }
}
