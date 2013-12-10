package com.genericgames.samurai.combat;

import com.genericgames.samurai.model.movable.State;

public class ChargeAttack extends Attack {

    private float chargeDuration;

    public ChargeAttack(float duration, float chargeDuration, int strength, State state) {
        super(duration, strength, state);
        this.chargeDuration = chargeDuration;
    }

    public float getChargeDuration() {
        return chargeDuration;
    }

    public void setChargeDuration(float chargeDuration) {
        this.chargeDuration = chargeDuration;
    }
}
