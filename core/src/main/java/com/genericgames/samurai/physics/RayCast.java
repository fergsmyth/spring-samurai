package com.genericgames.samurai.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

import java.util.Collection;

public class RayCast implements RayCastCallback {

    private Fixture f;
    private Vector2 point;
    private Vector2 normal;
    private float fraction;
    Collection<Fixture> ignoredFixtures;

    public RayCast(Collection<Fixture> ignoredFixtures) {
        this.fraction = 1f;
        this.ignoredFixtures = ignoredFixtures;
    }

    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
		if(fixture.getFilterData().categoryBits == PhysicalWorldHelper.CATEGORY_INDESTRUCTIBLE ||
                fixture.getFilterData().categoryBits == PhysicalWorldHelper.CATEGORY_NPC_LIVING_BODY){

            this.f = fixture;
            this.point = point.cpy();
            this.normal = normal.cpy();
            this.fraction = fraction;

            return fraction;
        }
        //ignore collision with this fixture:
        return -1f;
    }

    public float getFraction() {
        return fraction;
    }
}
