package com.genericgames.samurai.model.state.living;

import com.genericgames.samurai.model.state.Stateful;

public interface Living extends Stateful {

    public void damage(int damage);

    public void heal(int amount);

    public void fullHeal();

    public boolean isAlive();

    public int getHealth();

    public void setHealth(int health);

    public int getMaxHealth();

    public void setMaxHealth(int maxHealth);
}
