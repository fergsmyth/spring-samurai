package com.genericgames.samurai.model.movable.living.playable;

import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.model.movable.State;

public class PlayerCharacter extends Playable {

    public PlayerCharacter(){
        super();
        this.addAttack(new Attack(8, 0, 30, State.LIGHT_ATTACKING));
        this.addAttack(new Attack(8, 60, 50, State.HEAVY_ATTACKING));
    }
}
