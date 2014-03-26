package com.genericgames.samurai.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.genericgames.samurai.ai.AIHelper;
import com.genericgames.samurai.maths.MyMathUtils;
import com.genericgames.samurai.model.Collidable;
import com.genericgames.samurai.model.PlayerCharacter;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.Movable;
import com.genericgames.samurai.model.movable.living.Living;
import com.genericgames.samurai.model.movable.living.ai.Enemy;
import com.genericgames.samurai.utility.CoordinateSystem;
import com.genericgames.samurai.utility.MovementVector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class PhysicalWorldHelper {

    //Must be powers of 2, i.e. 0×1, 0×2, 0×4, 0×8, 0×10, 0×20, 0×40, 0×80…
    public static final short CATEGORY_ATTACK_FIELD = 0x0001;
    public static final short CATEGORY_FIELD_OF_VISION = 0x0002;
    public static final short CATEGORY_LIVING_BODY = 0x0004;
    public static final short CATEGORY_INDESTRUCTIBLE = 0x0008;
    public static final short CATEGORY_SUPPORT_CALL_FIELD = 0x0010;
    public static final short CATEGORY_CONVERSATION_FIELD = 0x0020;
    public static final short CATEGORY_NPC_LIVING_BODY = 0x0040;

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

    public static void moveBody(World world, Collidable collidable, float directionAngle, Vector2 linearVelocity){
        Body body = getBodyFor(collidable, world);
        body.setLinearVelocity(linearVelocity);
        body.setTransform(body.getPosition(), directionAngle);
    }

    public static void moveBody(World world, Collidable collidable, Vector2 direction, Vector2 linearVelocity){
        moveBody(world, collidable, CoordinateSystem.getRotationAngleInRadians(direction), linearVelocity);
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
        return fixture.getFilterData().categoryBits == CATEGORY_LIVING_BODY ||
                fixture.getFilterData().categoryBits == CATEGORY_NPC_LIVING_BODY;
    }

    public static boolean isConversation(Contact contact) {
        short categoryA = contact.getFixtureA().getFilterData().categoryBits;
        short categoryB = contact.getFixtureB().getFilterData().categoryBits;
        return categoryA == CATEGORY_CONVERSATION_FIELD && isPlayerB(contact)
                || isPlayerA(contact) && categoryB == CATEGORY_CONVERSATION_FIELD;
    }

    private static boolean isPlayerB(Contact contact) {
        return contact.getFixtureB().getFilterData().categoryBits == CATEGORY_LIVING_BODY
                && contact.getFixtureB().getBody().getUserData() instanceof PlayerCharacter;
    }

    private static boolean isPlayerA(Contact contact) {
        return contact.getFixtureA().getFilterData().categoryBits == CATEGORY_LIVING_BODY
                && contact.getFixtureA().getBody().getUserData() instanceof PlayerCharacter;
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
        Collection<Fixture> characterLivingBodyFixtures = new ArrayList<Fixture>();
        characterLivingBodyFixtures.add(PhysicalWorldHelper.getLivingBodyFixtureFor(character1, physicalWorld));
        characterLivingBodyFixtures.add(PhysicalWorldHelper.getLivingBodyFixtureFor(character2, physicalWorld));

        return clearLineBetween(character1.getX(), character1.getY(), character2.getX(), character2.getY(),
                characterLivingBodyFixtures, physicalWorld);
    }

    public static boolean clearLineBetween(float aX, float aY, float bX, float bY, Collection<Fixture> ignoredFixtures,
                                           World physicalWorld){
        RayCast rayCast = new RayCast(ignoredFixtures);
        physicalWorld.rayCast(rayCast,
                new Vector2(aX, aY), new Vector2(bX, bY));
        return rayCast.getFraction() == 1f;
    }

    public static boolean clearPathBetween(Living character1, Living character2, World physicalWorld){
        Collection<Fixture> characterLivingBodyFixtures = new HashSet<Fixture>();
        characterLivingBodyFixtures.add(PhysicalWorldHelper.getLivingBodyFixtureFor(character2, physicalWorld));

        return clearPathBetween(character1, character2.getX(), character2.getY(), characterLivingBodyFixtures,
                physicalWorld);
    }

    public static boolean clearPathBetween(Living character, float targetX, float targetY,
                                           Collection<Fixture> ignoredFixtures, World physicalWorld){
        Fixture livingBodyFixture = PhysicalWorldHelper.getLivingBodyFixtureFor(character, physicalWorld);
        ignoredFixtures.add(livingBodyFixture);
        Shape fixtureShape = livingBodyFixture.getShape();

        float halfFixtureWidth = getFixtureWidth(fixtureShape)/2;
        float angle = MyMathUtils.getAngleBetweenTwoPoints(character.getX(), character.getY(), targetX, targetY);

        boolean clearLineFromLeftRay = clearLineBetween(
                character.getX() + (halfFixtureWidth * ((float)Math.cos(angle))),
                character.getY() + (halfFixtureWidth * ((float)Math.sin(angle))),
                targetX + (halfFixtureWidth * ((float)Math.cos(angle))),
                targetY + (halfFixtureWidth * ((float)Math.sin(angle))),
                ignoredFixtures, physicalWorld);

        boolean clearLineFromRightRay = clearLineBetween(
                character.getX() - (halfFixtureWidth * ((float)Math.cos(-angle))),
                character.getY() + (halfFixtureWidth * ((float)Math.sin(-angle))),
                targetX - (halfFixtureWidth * ((float)Math.cos(-angle))),
                targetY + (halfFixtureWidth * ((float)Math.sin(-angle))),
                ignoredFixtures, physicalWorld);

        boolean clearLineFromCentreRay = clearLineBetween(character.getX(), character.getY(), targetX, targetY,
                ignoredFixtures, physicalWorld);

        return clearLineFromCentreRay && clearLineFromLeftRay && clearLineFromRightRay;
    }

    private static float getFixtureWidth(Shape fixtureShape){
        float fixtureWidth = 0.0f;
        if(fixtureShape.getType().equals(Shape.Type.Circle)){
            fixtureWidth = fixtureShape.getRadius()*2;
        }
        else if(fixtureShape.getType().equals(Shape.Type.Polygon)){
            fixtureWidth = getPolygonWidth((PolygonShape) fixtureShape);
        }
        return fixtureWidth;
    }

    private static float getFixtureHeight(Shape fixtureShape){
        float fixtureHeight = 0.0f;
        if(fixtureShape.getType().equals(Shape.Type.Circle)){
            fixtureHeight = fixtureShape.getRadius()*2;
        }
        else if(fixtureShape.getType().equals(Shape.Type.Polygon)){
            fixtureHeight = getPolygonHeight((PolygonShape) fixtureShape);
        }
        return fixtureHeight;
    }

    private static float getPolygonWidth(PolygonShape polygonShape){
        if(polygonShape.getVertexCount() == 4){
            List<Vector2> vertices = getVertices(polygonShape);
            //the distance from the edge of the polygon to its centre, multiplied by 2
            return vertices.get(0).x*2;
        }
        else {
            throw new IllegalArgumentException("This method should only be called for quadrilateral shapes.");
        }
    }
    private static float getPolygonHeight(PolygonShape polygonShape){
        if(polygonShape.getVertexCount() == 4){
            List<Vector2> vertices = getVertices(polygonShape);
            //the distance from the edge of the polygon to its centre, multiplied by 2
            return vertices.get(0).y*2;
        }
        else {
            throw new IllegalArgumentException("This method should only be called for quadrilateral shapes.");
        }
    }

    private static List<Vector2> getVertices(PolygonShape polygonShape){
        List<Vector2> vertices = new ArrayList<Vector2>();
        for(int i=0; i<polygonShape.getVertexCount(); i++){
            vertices.add(getVertex(polygonShape, i));
        }
        return vertices;
    }

    private static Vector2 getVertex(PolygonShape polygonShape, int vertexIndex) {
        Vector2 vertex = new Vector2();
        polygonShape.getVertex(vertexIndex, vertex);
        return vertex;
    }

    public static MovementVector getMovementVectorFor(Movable movableObject) {
        return new MovementVector(MyMathUtils.getVectorFromPointAndAngle(
                movableObject.getX(), movableObject.getY(),
                movableObject.getRotation()));
    }
}
