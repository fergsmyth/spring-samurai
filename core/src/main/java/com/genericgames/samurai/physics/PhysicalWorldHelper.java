package com.genericgames.samurai.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.genericgames.samurai.ai.AIHelper;
import com.genericgames.samurai.audio.SoundEffectCache;
import com.genericgames.samurai.combat.CombatHelper;
import com.genericgames.samurai.maths.MyMathUtils;
import com.genericgames.samurai.model.Checkpoint;
import com.genericgames.samurai.model.Collidable;
import com.genericgames.samurai.model.PlayerCharacter;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.Movable;
import com.genericgames.samurai.model.movable.character.WorldCharacter;
import com.genericgames.samurai.model.movable.character.ai.enemies.Enemy;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.state.living.Living;
import com.genericgames.samurai.model.state.living.combatable.Combatable;
import com.genericgames.samurai.model.weapon.Quiver;
import com.genericgames.samurai.screens.WorldRenderer;
import com.genericgames.samurai.utility.CoordinateSystem;
import com.genericgames.samurai.utility.MovementVector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PhysicalWorldHelper {

    //Must be powers of 2, i.e. 0×1, 0×2, 0×4, 0×8, 0×10, 0×20, 0×40, 0×80…
    public static final short CATEGORY_LIGHT_ATTACK_FIELD = 0x0001;
    public static final short CATEGORY_FIELD_OF_VISION = 0x0002;
    public static final short CATEGORY_LIVING_BODY = 0x0004;
    public static final short CATEGORY_INDESTRUCTIBLE = 0x0008;
    public static final short CATEGORY_CONVERSATION_FIELD = 0x0020;
    public static final short CATEGORY_NPC_BODY = 0x0040;
    public static final short CATEGORY_QUIVER = 0x0080;
    public static final short CATEGORY_ARROW = 0x0100;
    public static final short CHECKPOINT_CATEGORY = 0x0200;
    public static final short CATEGORY_IMPASSABLE_GATE = 0x0400;
    public static final short CATEGORY_HEAVY_ATTACK_FIELD = 0x0800;
//    public static final short  = 0x1000;

    public static final short MASK_AI = CATEGORY_LIGHT_ATTACK_FIELD | CATEGORY_HEAVY_ATTACK_FIELD |
            CATEGORY_LIVING_BODY | CATEGORY_INDESTRUCTIBLE |
            CATEGORY_NPC_BODY |
            CATEGORY_ARROW;

    public static final short MASK_OTHER = CATEGORY_LIGHT_ATTACK_FIELD | CATEGORY_HEAVY_ATTACK_FIELD |
            CATEGORY_FIELD_OF_VISION | CATEGORY_LIVING_BODY | CATEGORY_INDESTRUCTIBLE |
            CATEGORY_CONVERSATION_FIELD | CATEGORY_NPC_BODY |
            CATEGORY_ARROW | CATEGORY_QUIVER | CHECKPOINT_CATEGORY |
            CATEGORY_IMPASSABLE_GATE;
    
    public static final short MASK_QUIVER = CATEGORY_LIVING_BODY;

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

    public static void handleEnemyAI(SamuraiWorld samuraiWorld) {
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

    public static boolean isLightAttackField(Fixture fixture) {
        return fixture.getFilterData().categoryBits == CATEGORY_LIGHT_ATTACK_FIELD;
    }

    public static boolean isHeavyAttackField(Fixture fixture) {
        return fixture.getFilterData().categoryBits == CATEGORY_HEAVY_ATTACK_FIELD;
    }

    public static boolean isLivingBody(Fixture fixture) {
        return isBodyFixture(fixture) && fixture.getBody().getUserData() instanceof Living;
    }

    public static boolean isBodyFixture(Fixture fixture) {
        return fixture.getFilterData().categoryBits == CATEGORY_LIVING_BODY ||
                fixture.getFilterData().categoryBits == CATEGORY_NPC_BODY;
    }

    public static boolean isConversation(Contact contact) {
        short categoryA = contact.getFixtureA().getFilterData().categoryBits;
        short categoryB = contact.getFixtureB().getFilterData().categoryBits;
        return categoryA == CATEGORY_CONVERSATION_FIELD && isPlayerB(contact)
                || isPlayerA(contact) && categoryB == CATEGORY_CONVERSATION_FIELD;
    }

    public static void handleArrowCollision(Contact contact) {
        Body arrowBody;
        Fixture collidedFixture;
        if(isArrow(contact.getFixtureA())){
            arrowBody = contact.getFixtureA().getBody();
            collidedFixture = contact.getFixtureB();
        }
        else {
            arrowBody = contact.getFixtureB().getBody();
            collidedFixture = contact.getFixtureA();
        }

        SamuraiWorld sWorld = WorldRenderer.getRenderer().getWorld();
        if (isLivingBody(collidedFixture)){
            Living attackedChar = (Living) collidedFixture.getBody().getUserData();
            if(attackedChar.getState().equals(State.BLOCKING)){
                SoundEffectCache.swordClash.play(1.0f);
            }
            else if(!attackedChar.isInvincible()){
                attackedChar.damage(100, sWorld);
                CombatHelper.emitBloodSplatter(arrowBody.getAngle()+(float)Math.PI, attackedChar, sWorld);
            }
        }
        sWorld.addObjectToDelete((Arrow) arrowBody.getUserData());
    }

    private static Living findLivingCollidedBody(Contact contact, short categoryA, short categoryB) {
        Living collidedBody = null;
        if (categoryB == CATEGORY_LIVING_BODY){
            collidedBody = ((Living) contact.getFixtureB().getBody().getUserData());
        } else if (categoryA == CATEGORY_LIVING_BODY){
            collidedBody = ((Living) contact.getFixtureA().getBody().getUserData());
        }
        return collidedBody;
    }

    private static Body findArrow(Contact contact, short categoryA, short categoryB) {
        Body arrow = null;
        if (categoryA == CATEGORY_ARROW){
            arrow = contact.getFixtureA().getBody();
        } else if(categoryB == CATEGORY_ARROW) {
            arrow = contact.getFixtureB().getBody();
        }
        return arrow;
    }

    private static boolean isPlayerB(Contact contact) {
        return contact.getFixtureB().getFilterData().categoryBits == CATEGORY_LIVING_BODY
                && contact.getFixtureB().getBody().getUserData() instanceof PlayerCharacter;
    }

    private static boolean isPlayerA(Contact contact) {
        return contact.getFixtureA().getFilterData().categoryBits == CATEGORY_LIVING_BODY
                && contact.getFixtureA().getBody().getUserData() instanceof PlayerCharacter;
    }

    public static boolean isPlayerBodyFixture(Fixture fixture) {
        return isBodyFixture(fixture) &&
                fixture.getBody().getUserData() instanceof PlayerCharacter;
    }

    public static boolean isEnemyBodyFixture(Fixture fixture) {
        return isBodyFixture(fixture) &&
                fixture.getBody().getUserData() instanceof Enemy;
    }

    public static boolean isFieldOfVision(Fixture fixture) {
        return fixture.getFilterData().categoryBits == CATEGORY_FIELD_OF_VISION;
    }

    public static boolean isArrow(Fixture fixture) {
        return fixture.getFilterData().categoryBits == CATEGORY_ARROW;
    }

    public static boolean isCheckpoint(Fixture fixture) {
        return fixture.getFilterData().categoryBits == CHECKPOINT_CATEGORY;
    }

    public static boolean isQuiver(Fixture fixture) {
        return fixture.getFilterData().categoryBits == CATEGORY_QUIVER;
    }

    public static boolean isEnemyFieldOfVision(Fixture fixture) {
        return isFieldOfVision(fixture) &&
                isEnemyFixture(fixture);
    }

    /**
     * is an enemy field of vision or hearing field
     */
    public static boolean isEnemyAwarenessField(Fixture fixture) {
        return isEnemyFieldOfVision(fixture);
    }

    private static boolean isEnemyFixture(Fixture fixture) {
        return fixture.getBody().getUserData() instanceof Enemy;
    }

    public static Fixture getAttackFieldFor(Combatable character, State attackState, World world){
        if(attackState.equals(State.LIGHT_ATTACKING)){
            return getLightAttackField(character, world);
        }
        else if(attackState.equals(State.HEAVY_ATTACKING)) {
            return getHeavyAttackField(character, world);
        }
        throw new IllegalArgumentException("No sensor fixture was found for Living object: "+character+".");
    }

    public static Fixture getHeavyAttackField(Combatable character, World world){
        for(Fixture fixture : getBodyFor(character, world).getFixtureList()){
            if(isHeavyAttackField(fixture)){
                return fixture;
            }
        }
        throw new IllegalArgumentException("No Heavy Attack fixture was found for Living object: "+character+".");
    }

    public static Fixture getLightAttackField(Combatable character, World world){
        for(Fixture fixture : getBodyFor(character, world).getFixtureList()){
            if(isLightAttackField(fixture)){
                return fixture;
            }
        }
        throw new IllegalArgumentException("No Light Attack fixture was found for Living object: "+character+".");
    }

    public static Fixture getFieldOfVisionFieldFor(Enemy enemy, World world){
        for(Fixture fixture : getBodyFor(enemy, world).getFixtureList()){
            if(isFieldOfVision(fixture)){
                return fixture;
            }
        }
        throw new IllegalArgumentException("No Field of Vision fixture was found for Enemy object: "+enemy+".");
    }

    public static Fixture getBodyFixtureFor(Movable character, World world){
        for(Fixture fixture : getBodyFor(character, world).getFixtureList()){
            if(isBodyFixture(fixture)){
                return fixture;
            }
        }
        throw new IllegalArgumentException("No living body fixture was found for object: "+character+".");
    }

    public static boolean isBetweenPlayerAndEnemyAwarenessField(Contact contact) {
        return (
                (isEnemyAwarenessField(contact.getFixtureA()) &&
                        isPlayerBodyFixture(contact.getFixtureB()))
                        ||
                        (isEnemyAwarenessField(contact.getFixtureB()) &&
                                isPlayerBodyFixture(contact.getFixtureA()))
        );
    }

    public static boolean isBetweenArrowAndFOV(Contact contact) {
        return (
                (isArrow(contact.getFixtureA()) &&
                        isFieldOfVision(contact.getFixtureB()))
                        ||
                        (isArrow(contact.getFixtureB()) &&
                                isFieldOfVision(contact.getFixtureA()))
        );
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

    public static boolean clearLineBetween(Movable character1, Movable character2, World physicalWorld){
        Collection<Fixture> characterLivingBodyFixtures = new ArrayList<Fixture>();
        characterLivingBodyFixtures.add(getBodyFixtureFor(character1, physicalWorld));
        characterLivingBodyFixtures.add(getBodyFixtureFor(character2, physicalWorld));

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

    public static boolean clearPathBetween(WorldCharacter character, float targetX, float targetY,
                                           Collection<Fixture> ignoredFixtures, World physicalWorld){
        Fixture livingBodyFixture = getBodyFixtureFor(character, physicalWorld);
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

    /**
     * returns true if the collision is between an arrow and any other physical (non-sensor) object
     */
    public static boolean isBetweenArrowAndPhysicalObject(Contact contact) {
        return (
                (isArrow(contact.getFixtureA()) &&
                        !contact.getFixtureB().isSensor())
                        ||
                        (isArrow(contact.getFixtureB()) &&
                                !contact.getFixtureA().isSensor())
        );
    }

    public static boolean isCheckpoint(Contact contact) {
        return  isCheckpoint(contact.getFixtureA()) &&
                        isPlayerBodyFixture(contact.getFixtureB())
                ||
                isCheckpoint(contact.getFixtureB()) &&
                        isPlayerBodyFixture(contact.getFixtureA());
    }

    public static void flagCheckpointForDeletion(Contact contact){
        SamuraiWorld sWorld = WorldRenderer.getRenderer().getWorld();
        sWorld.addObjectToDelete((Checkpoint) getBodyByCategory(contact, CHECKPOINT_CATEGORY).getUserData());
    }

    private static Body getBodyByCategory(Contact contact, short category){
        if (contact.getFixtureA().getFilterData().categoryBits == category){
            return contact.getFixtureA().getBody();
        } else if (contact.getFixtureB().getFilterData().categoryBits == category){
            return contact.getFixtureB().getBody();
        } return null;
    }

    public static boolean isBetweenPlayerAndQuiver(Contact contact) {
        return (
                (isQuiver(contact.getFixtureA()) &&
                        isPlayerBodyFixture(contact.getFixtureB()))
                        ||
                        (isQuiver(contact.getFixtureB()) &&
                                isPlayerBodyFixture(contact.getFixtureA()))
        );
    }

    public static void handleQuiverCollision( Contact contact, SamuraiWorld samuraiWorld) {
        PlayerCharacter player = getPlayer(contact);
        Quiver quiver = getQuiver(contact);
        quiver.pickup(samuraiWorld, player);
    }

    private static Quiver getQuiver(Contact contact) {
        if(isQuiver(contact.getFixtureA())){
            return (Quiver) contact.getFixtureA().getBody().getUserData();
        }
        else if(isQuiver(contact.getFixtureB())){
            return (Quiver) contact.getFixtureB().getBody().getUserData();
        }
        return null;
    }

    private static PlayerCharacter getPlayer(Contact contact) {
        if(isPlayerBodyFixture(contact.getFixtureA())){
            return (PlayerCharacter) contact.getFixtureA().getBody().getUserData();
        }
        else if(isPlayerBodyFixture(contact.getFixtureB())){
            return (PlayerCharacter) contact.getFixtureB().getBody().getUserData();
        }
        return null;
    }
}
