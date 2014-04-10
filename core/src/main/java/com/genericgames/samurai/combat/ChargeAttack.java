package com.genericgames.samurai.combat;

import com.genericgames.samurai.model.state.State;

public class ChargeAttack extends Attack {

    private int chargeDuration;

    public ChargeAttack(int attackDuration, int recoveryDuration,
                        int chargeDuration, int strength, State state){
        super(attackDuration, recoveryDuration, strength, state);
        this.chargeDuration = chargeDuration;
    }

    public float getChargeDuration() {
        return chargeDuration;
    }

    public void setChargeDuration(int chargeDuration) {
        this.chargeDuration = chargeDuration;
    }
}
