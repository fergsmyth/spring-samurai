package com.example.mylibgdxgame.model.movable;

import com.example.mylibgdxgame.model.Collidable;
import com.example.mylibgdxgame.model.WorldObject;

/**
 * Any movable object, e.g. projectiles, vehicles, creatures, characters (including playable)
 */
public abstract class Movable extends WorldObject implements Collidable {

    private static final float DEFAULT_SPEED = 5f;

    // a constant dictating the speed at which the object moves (when it does):
    private float speed;
    // true if the object always faces the direction in which it is travelling. Otherwise, false.
    private boolean directionFacing = true;

    public Movable(){
        super();
        this.setSpeed(DEFAULT_SPEED);
    }

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getSpeed() {
		return speed;
	}

    public boolean isDirectionFacing() {
        return directionFacing;
    }

    public void setDirectionFacing(boolean directionFacing) {
        this.directionFacing = directionFacing;
    }
}
