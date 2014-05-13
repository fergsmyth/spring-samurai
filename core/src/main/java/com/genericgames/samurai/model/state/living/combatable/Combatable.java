package com.genericgames.samurai.model.state.living.combatable;

import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.model.state.living.Living;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Combatable extends Living {

    private Collection<Attack> attacks = new ArrayList<Attack>();

    public Combatable(){
    }

    public Collection<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(Collection<Attack> attacks) {
        this.attacks = attacks;
    }

    public void addAttack(Attack attack){
        this.attacks.add(attack);
    }
}
