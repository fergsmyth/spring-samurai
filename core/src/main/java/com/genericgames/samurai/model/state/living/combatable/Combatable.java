package com.genericgames.samurai.model.state.living.combatable;

import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.model.state.living.Living;
import com.genericgames.samurai.model.weapon.WeaponInventory;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Combatable extends Living {

    private Collection<Attack> attacks = new ArrayList<Attack>();
    private WeaponInventory weaponInventory = new WeaponInventory();

    public Combatable(float x, float y){
        super(x, y);
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

    public WeaponInventory getWeaponInventory() {
        return weaponInventory;
    }

    public void setWeaponInventory(WeaponInventory weaponInventory) {
        this.weaponInventory = weaponInventory;
    }
}
