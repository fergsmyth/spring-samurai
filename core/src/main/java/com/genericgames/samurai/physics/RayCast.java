package com.genericgames.samurai.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class RayCast implements RayCastCallback {

    private Fixture f;
    private Vector2 point;
    private float fraction;

    public RayCast() {
        this.fraction = 1f;
    }

    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
        if(fixture.getFilterData().categoryBits == PhysicalWorldHelper.CATEGORY_INDESTRUCTIBLE){
                this.f = fixture;
                this.point = point;
                this.fraction = fraction;
            return fraction;
        }
        return -1f;
    }

    public float getFraction() {
        return fraction;
    }
}
