package com.genericgames.samurai.model.movable.character.ai;

import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.ai.patrolpattern.PatrolPatternGroup;
import com.genericgames.samurai.ai.performers.AIActionPerformer;
import com.genericgames.samurai.ai.routefinding.Route;
import com.genericgames.samurai.ai.patrolpattern.PatrolPattern;
import com.genericgames.samurai.model.movable.character.WorldCharacter;

import java.util.ArrayList;

public abstract class AI extends WorldCharacter {

    private AIActionPerformer actionPerformer;

    private PatrolPatternGroup patrolPatternGroup = new PatrolPatternGroup();
    private Route route = new Route();
    private boolean playerAware = false;

    public AI(float x, float y){
        super(x, y);

        //TODO Replace with retrieval of pattern from level tmx file:
        ArrayList<Vector2> patrolPoints = new ArrayList<Vector2>();
        patrolPoints.add(new Vector2(x, y));
        patrolPoints.add(new Vector2(x, y+3));
        this.patrolPatternGroup.addPatrolPattern(new PatrolPattern(patrolPoints));
        ArrayList<Vector2> patrolPoints2 = new ArrayList<Vector2>();
        patrolPoints2.add(new Vector2(x+1, y+3));
        patrolPoints2.add(new Vector2(x+1, y));
        this.patrolPatternGroup.addPatrolPattern(new PatrolPattern(patrolPoints2));
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
