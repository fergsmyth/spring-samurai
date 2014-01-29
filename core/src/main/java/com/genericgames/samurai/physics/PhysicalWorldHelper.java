package com.genericgames.samurai.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.genericgames.samurai.model.Collidable;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.living.Living;
import com.genericgames.samurai.utility.CoordinateSystem;

public class PhysicalWorldHelper {

    public static final short CATEGORY_ATTACK_FIELD = 0x0001;
    public static final short CATEGORY_FIELD_OF_VISION = 0x0002;
    public static final short CATEGORY_LIVING_BODY = 0x0004;
    public static final short CATEGORY_INDESTRUCTIBLE = 0x0008;

    public static void checkForCollisions(SamuraiWorld samuraiWorld) {
        World physicalWorld = samuraiWorld.getPhysicalWorld();

        physicalWorld.step(1 / 20f, 1, 1);

        Array<Body> bodies = new Array<Body>();
        physicalWorld.getBodies(bodies);

        for (Body b : bodies){
            Collidable c = (Collidable) b.getUserData();

            if (c != null) {
                c.setPosition(b.getPosition().x, b.getPosition().y);
                c.setRotation(b.getAngle());
            }
        }
    }

    public static void moveBody(World world, Collidable collidable, Vector2 direction, Vector2 linearVelocity){
        Body body = getBodyFor(collidable, world);
        body.setLinearVelocity(linearVelocity);
        Vector2 mouseVector = CoordinateSystem.translateMouseToLocalPosition(direction);
        body.setTransform(body.getPosition(), CoordinateSystem.getRotationAngleInRadians(mouseVector));
    }

    private static Body getBodyFor(Collidable collidable, World world) {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body b : bodies){

            Collidable c = (Collidable) b.getUserData();
            if(c == collidable){
                return b;
            }
        }
        throw new IllegalArgumentException("No matching body was found for Collidable: "+collidable+".");
    }

    public static boolean isAttackField(Fixture fixture) {
        return fixture.getFilterData().categoryBits == CATEGORY_ATTACK_FIELD;
    }

    public static boolean isLivingBody(Fixture fixture) {
        return fixture.getFilterData().categoryBits == CATEGORY_LIVING_BODY;
    }

    public static boolean isFieldOfVision(Fixture fixture) {
        return fixture.getFilterData().categoryBits == CATEGORY_FIELD_OF_VISION;
    }

    public static Fixture getAttackFieldFor(Living character, World world){
        for(Fixture fixture : getBodyFor(character, world).getFixtureList()){
            if(isAttackField(fixture)){
                return fixture;
            }
        }
        throw new IllegalArgumentException("No sensor fixture was found for Living object: "+character+".");
    }
}
