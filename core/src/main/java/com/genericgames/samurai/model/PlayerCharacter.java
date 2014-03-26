package com.genericgames.samurai.model;

import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.combat.ChargeAttack;
import com.genericgames.samurai.inventory.Inventory;
import com.genericgames.samurai.inventory.Item;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.Living;

public class PlayerCharacter extends Living {

    private Inventory inventory = new Inventory();

    public PlayerCharacter(){
        super();
        this.setSpeed(DEFAULT_SPEED);
        this.addAttack(new Attack(8, 30, State.LIGHT_ATTACKING));
        this.addAttack(new ChargeAttack(8, 60, 50, State.HEAVY_ATTACKING));
        inventory.addItem(new Item("bow.png"));
        inventory.addItem(new Item("potion.png"));
        inventory.addItem(new Item("sword.png"));

    }

    public Inventory getInventory(){
        return inventory;
    }

    public void setPosition(float positonX, float positonY){
        setPositionX(positonX);
        setPositionY(positonY);
    }
}
