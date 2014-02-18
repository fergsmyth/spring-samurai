package com.genericgames.samurai.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.genericgames.samurai.ai.AIHelper;
import com.genericgames.samurai.model.Collidable;
import com.genericgames.samurai.model.PlayerCharacter;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.living.Living;
import com.genericgames.samurai.model.movable.living.ai.Enemy;
import com.genericgames.samurai.utility.CoordinateSystem;

public class PhysicalWorldHelper {

    public static final short CATEGORY_ATTACK_FIELD = 0x0001;
    public static final short CATEGORY_FIELD_OF_VISION = 0x0002;
    public static final short CATEGORY_LIVING_BODY = 0x0004;
    public static final short CATEGORY_INDESTRUCTIBLE = 0x0008;
    public static final short CATEGORY_SUPPORT_CALL_FIELD = 0x0010;

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

        handleEnemyAI(samuraiWorld);
    }

    private static void handleEnemyAI(SamuraiWorld samuraiWorld) {
        AIHelper.detectAIAwareness(samuraiWorld);

        AIHelper.handleAIActions(samuraiWorld);
    }

    public static void movePlayer(SamuraiWorld samuraiWorld, Vector2 direction, Vector2 linearVelocity){
        Vector2 mouseVector = CoordinateSystem.translateMouseToLocalPosition(direction);
        moveBody(samuraiWorld.getPhysicalWorld(), samuraiWorld.getPlayerCharacter(), mouseVector, linearVelocity);
    }

    public static void moveBody(World world, Collidable collidable, Vector2 direction, Vector2 linearVelocity){
        Body body = getBodyFor(collidable, world);
        body.setLinearVelocity(linearVelocity);
        body.setTransform(body.getPosition(), CoordinateSystem.getRotationAngleInRadians(direction));
    }

    public static Body getBodyFor(Collidable collidable, World world) {
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

    public static boolean isPlayerLivingBody(Fixture fixture) {
        return PhysicalWorldHelper.isLivingBody(fixture) &&
                fixture.getBody().getUserData() instanceof PlayerCharacter;
    }

    public static boolean isFieldOfVision(Fixture fixture) {
        return fixture.getFilterData().categoryBits == CATEGORY_FIELD_OF_VISION;
    }

    public static boolean isEnemyFieldOfVision(Fixture fixture) {
        return PhysicalWorldHelper.isFieldOfVision(fixture) &&
                isEnemyFixture(fixture);
    }

    public static boolean isSupportCallField(Fixture fixture) {
        return fixture.getFilterData().categoryBits == CATEGORY_SUPPORT_CALL_FIELD;
    }

    private static boolean isEnemyFixture(Fixture fixture) {
        return fixture.getBody().getUserData() instanceof Enemy;
    }

    public static Fixture getAttackFieldFor(Living character, World world){
        for(Fixture fixture : getBodyFor(character, world).getFixtureList()){
            if(isAttackField(fixture)){
                return fixture;
            }
        }
        throw new IllegalArgumentException("No sensor fixture was found for Living object: "+character+".");
    }

    public static Fixture getLivingBodyFixtureFor(Living character, World world){
        for(Fixture fixture : getBodyFor(character, world).getFixtureList()){
            if(isLivingBody(fixture)){
                return fixture;
            }
        }
        throw new IllegalArgumentException("No living body fixture was found for object: "+character+".");
    }

    public static boolean isBetweenPlayerAndEnemyFOV(Contact contact) {
        return (
                (PhysicalWorldHelper.isEnemyFieldOfVision(contact.getFixtureA()) &&
                        PhysicalWorldHelper.isPlayerLivingBody(contact.getFixtureB()))
                        ||
                        (PhysicalWorldHelper.isEnemyFieldOfVision(contact.getFixtureB()) &&
                                PhysicalWorldHelper.isPlayerLivingBody(contact.getFixtureA()))
        );
    }

    public static boolean isBetweenSupportCallFields(Contact contact) {
        return PhysicalWorldHelper.isSupportCallField(contact.getFixtureA()) &&
                        PhysicalWorldHelper.isSupportCallField(contact.getFixtureB());
    }

    public static Enemy getEnemy(Contact contact){
        if(isEnemyFixture(contact.getFixtureA())){
            return (Enemy)contact.getFixtureA().getBody().getUserData();
        }
        else if(isEnemyFixture(contact.getFixtureB())){
            return (Enemy) contact.getFixtureB().getBody().getUserData();
        }
        throw new IllegalArgumentException("Neither fixture A or B is an Enemy!");
    }

    public static boolean clearLineBetween(Living character1, Living character2, World physicalWorld){
        return clearLineBetween(character1.getX(), character1.getY(), character2.getX(), character2.getY(), physicalWorld);
    }

    public static boolean clearLineBetween(float aX, float aY, float bX, float bY, World physicalWorld){
        RayCast rayCast = new RayCast();
        physicalWorld.rayCast(rayCast,
                new Vector2(aX, aY), new Vector2(bX, bY));
        return rayCast.getFraction() == 1f;
    }
}
