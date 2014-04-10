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

	public Movable(){
        super();
    }

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getSpeed() {
		return speed;
	}
}
