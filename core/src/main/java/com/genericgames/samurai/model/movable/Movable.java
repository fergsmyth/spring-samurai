package com.genericgames.samurai.model.movable;

import com.genericgames.samurai.model.Collidable;
import com.genericgames.samurai.model.WorldObject;

/**
 * Any movable object, e.g. projectiles, vehicles, creatures, characters (including playable)
 */
public abstract class Movable extends WorldObject implements Collidable {

    public static final float DEFAULT_SPEED = 2f;

    // a constant dictating the speed at which the object moves (when it does):
    private float speed;

	private float stateTime = 0;
	private State state = State.IDLE;

	public Movable(){
        super();
    }

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getSpeed() {
		return speed;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public float getStateTime() {
		return stateTime;
	}

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public void incrementStateTime() {
        stateTime = stateTime + 1;
    }
}
