package com.genericgames.samurai.model.movable.living.ai;

import com.genericgames.samurai.combat.TelegraphedAttack;
import com.genericgames.samurai.model.movable.State;

public class Enemy extends NPC {

    private static final float DEFAULT_SPEED = 1f;

    public Enemy(){
        this.setSpeed(DEFAULT_SPEED);
        this.addAttack(new TelegraphedAttack(45, 8, 20, 30, State.LIGHT_ATTACKING));
        this.addAttack(new TelegraphedAttack(45, 8, 20, 50, State.HEAVY_ATTACKING));
    }
}
