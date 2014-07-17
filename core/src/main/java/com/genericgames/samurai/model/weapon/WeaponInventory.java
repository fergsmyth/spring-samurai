package com.genericgames.samurai.model.weapon;

import java.util.ArrayList;
import java.util.List;

public class WeaponInventory {

    private List<Weapon> weapons;

    public WeaponInventory() {
        weapons = new ArrayList<Weapon>();
    }

    public void selectWeapon(Weapon weapon){
        weapons.remove(weapon);
        weapons.add(0, weapon);
    }

    /**
     * Moves last weapon in the list to first position.
     * All subsequent weapons give moved down 1 position.
     */
    public void cycleWeapons(){
        Weapon weaponToSelect = weapons.get(weapons.size()-1);
        selectWeapon(weaponToSelect);
    }

    public Weapon getSelectedWeapon(){
        return weapons.get(0);
    }

    public void addWeapon(Weapon weapon){
        weapons.add(weapon);
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }
}
