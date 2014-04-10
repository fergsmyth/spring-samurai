package com.genericgames.samurai.model.movable.character.ai;

import com.genericgames.samurai.ai.performers.AIActionPerformer;
import com.genericgames.samurai.ai.performers.AIActionPerformerProvider;
import com.genericgames.samurai.ai.routefinding.Route;
import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.model.movable.character.WorldCharacter;
import com.genericgames.samurai.model.state.living.combatable.Combatable;
import com.genericgames.samurai.model.state.living.combatable.CombatableImpl;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.state.Stateful;

import java.util.Collection;


public class AI extends WorldCharacter implements Stateful, Combatable {

    private AIActionPerformer actionPerformer;
    private Route route = new Route();
    private boolean playerAware = false;

    private Combatable combatable;

    public AI(){
        super();
        combatable = new CombatableImpl();
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


    public void damage(int damage){
        combatable.damage(damage);
        if(this.getHealth() > 0){
            setAIActionPerformer(AIActionPerformerProvider.getActionPerformer(ActionState.KNOCK_BACK, this));
        }
    }

    @Override
    public void heal(int amount) {
        combatable.heal(amount);
    }

    @Override
    public void fullHeal() {
        combatable.fullHeal();
    }

    @Override
    public boolean isAlive() {
        return combatable.isAlive();
    }

    @Override
    public int getHealth() {
        return combatable.getHealth();
    }

    @Override
    public void setHealth(int health) {
        combatable.setHealth(health);
    }

    @Override
    public int getMaxHealth() {
        return combatable.getMaxHealth();
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        combatable.setMaxHealth(maxHealth);
    }

    @Override
    public Collection<Attack> getAttacks() {
        return combatable.getAttacks();
    }

    @Override
    public void setAttacks(Collection<Attack> attacks) {
        combatable.setAttacks(attacks);
    }

    @Override
    public void addAttack(Attack attack) {
        combatable.addAttack(attack);
    }

    public State getState() {
        return combatable.getState();
    }

    public void setState(State state) {
        combatable.setState(state);
    }

    public float getStateTime() {
        return combatable.getStateTime();
    }

    public void setStateTime(float stateTime) {
        combatable.setStateTime(stateTime);
    }

    public void incrementStateTime() {
        combatable.incrementStateTime();
    }
}
