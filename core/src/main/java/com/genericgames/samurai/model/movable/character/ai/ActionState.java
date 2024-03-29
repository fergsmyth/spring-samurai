package com.genericgames.samurai.model.movable.character.ai;

import java.util.*;

public enum ActionState {
    IDLE, WALK_FORWARD, WALK_BACKWARDS, WALK_LEFT, WALK_RIGHT,
    LIGHT_ATTACK, HEAVY_ATTACK, BLOCK, DODGE_LEFT, DODGE_RIGHT, DODGE_BACKWARDS,
    //Involuntary actions:
    KNOCK_BACK;

    public static EnumSet<ActionState> getVoluntaryActionStates(){
        return EnumSet.of(IDLE, WALK_FORWARD, WALK_BACKWARDS, WALK_LEFT, WALK_RIGHT,
                LIGHT_ATTACK, HEAVY_ATTACK, BLOCK, DODGE_LEFT, DODGE_RIGHT, DODGE_BACKWARDS);
    }

    public boolean isInterruptible(){
        return this.equals(IDLE) || this.equals(WALK_FORWARD) || this.equals(WALK_BACKWARDS) ||
                this.equals(WALK_LEFT) || this.equals(WALK_RIGHT);
    }
}
