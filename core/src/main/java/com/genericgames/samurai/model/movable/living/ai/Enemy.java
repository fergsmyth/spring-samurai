package com.genericgames.samurai.model.movable.living.ai;

import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.model.movable.State;

public class Enemy extends NPC {

    private static final float DEFAULT_SPEED = 1f;

    public Enemy(){
        this.setSpeed(DEFAULT_SPEED);
        this.addAttack(new Attack(8, 30, State.LIGHT_ATTACKING));
        this.addAttack(new Attack(8, 50, State.HEAVY_ATTACKING));
    }
}
