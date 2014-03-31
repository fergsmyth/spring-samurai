package com.genericgames.samurai.model.movable;

public enum State {
	IDLE, WALKING, DEAD, LIGHT_ATTACKING, HEAVY_ATTACKING,
    TELEGRAPHING_LIGHT_ATTACK, TELEGRAPHING_HEAVY_ATTACK,
    CHARGING, CHARGED, BLOCKING, DODGE;

    public boolean isAttackCapable(){
        return this.isMoveCapable() || this.isCharging();
    }

    public boolean isMoveCapable(){
        return this.equals(IDLE) || this.equals(WALKING);
    }

    public boolean isBlockCapable(){
        return isMoveCapable();
    }

    public boolean isDodgeCapable() {
        return this.equals(WALKING);
    }

    public boolean isAttacking(){
        return this.equals(LIGHT_ATTACKING) || this.equals(HEAVY_ATTACKING);
    }

    /**
     * Indicates if the state is repeating; necessary for looping animations
     */
    public boolean isLoopingState(){
        return this.equals(WALKING) || this.equals(IDLE) || this.isCharging()
                || this.equals(BLOCKING) || this.equals(DODGE);
    }

    public boolean isCharging() {
        return this.equals(CHARGING) || this.equals(CHARGED);
    }

    public boolean isDodging() {
        return this.equals(DODGE);
    }
}
