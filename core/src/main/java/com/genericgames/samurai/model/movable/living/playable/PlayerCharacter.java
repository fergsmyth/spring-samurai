package com.genericgames.samurai.model.movable.living.playable;

import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.model.movable.State;

public class PlayerCharacter extends Playable {

    public PlayerCharacter(){
        super();
        this.addAttack(new Attack(20, 30, State.LIGHT_ATTACKING));
    }
}
