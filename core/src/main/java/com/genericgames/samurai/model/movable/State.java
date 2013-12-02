package com.genericgames.samurai.model.movable;

public enum State {
	IDLE, WALKING, DEAD, LIGHT_ATTACKING, HEAVY_ATTACKING;

    public boolean isAttackCapable(){
        return this.equals(IDLE) || this.equals(WALKING);
    }

    public boolean isMoveCapable(){
        return this.equals(IDLE) || this.equals(WALKING);
    }

    public boolean isAttacking(){
        return this.equals(LIGHT_ATTACKING) || this.equals(HEAVY_ATTACKING);
    }

    public boolean isLoopingState(){
        return this.equals(WALKING) || this.equals(IDLE);
    }
}
