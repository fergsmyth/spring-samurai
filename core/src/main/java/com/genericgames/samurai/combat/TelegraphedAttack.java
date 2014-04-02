package com.genericgames.samurai.combat;

import com.genericgames.samurai.model.movable.State;

public class TelegraphedAttack extends Attack {

    private int telegraphDuration;

    public TelegraphedAttack(int telegraphDuration, int attackDuration, int recoveryDuration, int strength, State state) {
        super(attackDuration, recoveryDuration, strength, state);
        this.telegraphDuration = telegraphDuration;
    }

    public int getTelegraphDuration() {
        return telegraphDuration;
    }

    public void setTelegraphDuration(int telegraphDuration) {
        this.telegraphDuration = telegraphDuration;
    }

    public int getInflictionFrame(){
        return getTelegraphDuration()+getAttackDuration()-1;
    }

    public int getTotalDuration(){
        return getTelegraphDuration()+getAttackDuration()+getRecoveryDuration();
    }
}
