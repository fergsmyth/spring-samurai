package com.genericgames.samurai.model.movable;

public enum State {
	IDLE, WALKING, DEAD, LIGHT_ATTACKING, HEAVY_ATTACKING, CHARGING, CHARGED, BLOCKING;

    public boolean isAttackCapable(){
        return this.equals(IDLE) || this.equals(WALKING) || this.isCharging();
    }

    public boolean isMoveCapable(){
        return this.equals(IDLE) || this.equals(WALKING);
    }

    public boolean isBlockCapable(){
        return this.equals(IDLE) || this.equals(WALKING);
    }

    public boolean isAttacking(){
        return this.equals(LIGHT_ATTACKING) || this.equals(HEAVY_ATTACKING);
    }

    /**
     * Indicates if the state is repeating; necessary for looping animations
     */
    public boolean isLoopingState(){
        return this.equals(WALKING) || this.equals(IDLE) || this.isCharging() || this.equals(BLOCKING);
    }

    public boolean isCharging() {
        return this.equals(CHARGING) || this.equals(CHARGED);
    }
}
