package com.genericgames.samurai.model.state.living.combatable;

import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.model.state.living.Living;

import java.util.Collection;

public interface Combatable extends Living {

    public Collection<Attack> getAttacks();

    public void setAttacks(Collection<Attack> attacks);

    public void addAttack(Attack attack);
}
