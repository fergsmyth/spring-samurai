package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.combat.ChargeAttack;
import com.genericgames.samurai.inventory.Inventory;
import com.genericgames.samurai.inventory.Item;
import com.genericgames.samurai.model.movable.character.WorldCharacter;
import com.genericgames.samurai.model.state.living.combatable.Combatable;
import com.genericgames.samurai.model.state.living.combatable.CombatableImpl;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.state.Stateful;

import java.util.Collection;

public class PlayerCharacter extends WorldCharacter implements Stateful, Combatable {

    private Inventory inventory = new Inventory();

    private Combatable combatable;

    public PlayerCharacter(){
        super();
        combatable = new CombatableImpl();
        this.setSpeed(DEFAULT_SPEED);
        this.addAttack(new Attack(8, 15, 30, State.LIGHT_ATTACKING));
        this.addAttack(new ChargeAttack(8, 15, 60, 50, State.HEAVY_ATTACKING));
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

    @Override
    public void draw(SpriteBatch batch) {

    }

    public State getState() {
        return combatable.getState();
    }

    public void setState(State state) {
        combatable.setState(state);
    }

    public float getStateTime() {
        return combatable.getStateTime();
    }

    public void setStateTime(float stateTime) {
        combatable.setStateTime(stateTime);
    }

    public void incrementStateTime() {
        combatable.incrementStateTime();
    }


    public void damage(int damage){
        combatable.damage(damage);
    }

    @Override
    public void heal(int amount) {
        combatable.heal(amount);
    }

    @Override
    public void fullHeal() {
        combatable.fullHeal();
    }

    @Override
    public boolean isAlive() {
        return combatable.isAlive();
    }

    @Override
    public int getHealth() {
        return combatable.getHealth();
    }

    @Override
    public void setHealth(int health) {
        combatable.setHealth(health);
    }

    @Override
    public int getMaxHealth() {
        return combatable.getMaxHealth();
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        combatable.setMaxHealth(maxHealth);
    }

    @Override
    public Collection<Attack> getAttacks() {
        return combatable.getAttacks();
    }

    @Override
    public void setAttacks(Collection<Attack> attacks) {
        combatable.setAttacks(attacks);
    }

    @Override
    public void addAttack(Attack attack) {
        combatable.addAttack(attack);
    }
}
