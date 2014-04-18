package com.genericgames.samurai.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.genericgames.samurai.model.WorldObject;
import com.genericgames.samurai.model.movable.character.WorldCharacter;

public class PhysicalWorldFactory {

    public static Arrow createArrow(float x, float y, Vector2 direction, World physicalWorld){
        return new Arrow(x, y, direction, physicalWorld);
    }

    public static void createPlayer(WorldCharacter character, World physicalWorld) {
        Body body = createPhysicalCharacter(character, physicalWorld, BodyDef.BodyType.DynamicBody,
                PhysicalWorldHelper.CATEGORY_LIVING_BODY);

        createAttackFieldFixture(body);
        createCombatZoneFixture(body);
    }

    public static void createEnemy(WorldCharacter character, World physicalWorld) {
        Body body = createPhysicalCharacter(character, physicalWorld, BodyDef.BodyType.DynamicBody,
                PhysicalWorldHelper.CATEGORY_LIVING_BODY);

        createAttackFieldFixture(body);
        createFieldOfVisionFixture(body);
        createSupportCallFixture(body);
    }

    public static void createNPC(WorldCharacter character, World physicalWorld) {
        Body body = createPhysicalCharacter(character, physicalWorld, BodyType.StaticBody,
                PhysicalWorldHelper.CATEGORY_NPC_BODY);
        createConversationFixture(body);
    }

    private static Body createPhysicalCharacter(WorldCharacter character, World physicalWorld, BodyType type,
                                                short livingBodyCategory) {
		float bodyWidth = 0.35f;
		float bodyHeight = 0.35f;
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = type;
		// Set our body's starting position in the world
		bodyDef.position.set(character.getX(), character.getY());

		// Create our body in the world using our body definition
		Body body = physicalWorld.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(bodyWidth, bodyHeight);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.density = 0f;
		fixtureDef.friction = 0f;
		fixtureDef.restitution = 0.6f;
        fixtureDef.filter.categoryBits = livingBodyCategory;

		body.createFixture(fixtureDef);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		polygonShape.dispose();

		body.setUserData(character);

        return body;
	}

    public static void createDoor(WorldObject worldObject, World physicalWorld, float bodyWidth, float bodyHeight){
        CircleShape circle = new CircleShape();
        circle.setRadius(8);
        createPhysicalWorldObject(worldObject, physicalWorld, bodyWidth, bodyHeight);
    }

    public static void createPhysicalWorldObject(WorldObject worldObject, World physicalWorld, float bodyWidth, float bodyHeight) {

		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.StaticBody;
		// Set our body's starting position in the world
		bodyDef.position.set(worldObject.getX() + 0.5f, worldObject.getY() + 0.5f);

		// Create our body in the world using our body definition
		Body body = physicalWorld.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
        PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(bodyWidth, bodyHeight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = false;
        fixtureDef.friction = 0f;
        fixtureDef.filter.categoryBits = PhysicalWorldHelper.CATEGORY_INDESTRUCTIBLE;

        body.createFixture(fixtureDef);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		polygonShape.dispose();

		body.setUserData(worldObject);
	}

    public static void createAttackFieldFixture(Body body) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.35f, 0.1f, new Vector2(0f, -0.5f), 0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.friction = 0f;
        fixtureDef.filter.categoryBits = PhysicalWorldHelper.CATEGORY_ATTACK_FIELD;

        body.createFixture(fixtureDef);
    }

    public static void createConversationFixture(Body body) {
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circle = new CircleShape();
        circle.setRadius(1);

        fixtureDef.shape = circle;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = PhysicalWorldHelper.CATEGORY_CONVERSATION_FIELD;

        body.createFixture(fixtureDef);

    }

    public static void createFieldOfVisionFixture(Body body){
        float radius = 8;
        int fieldAngle = 75;
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        int numVertices = 8;
        Vector2 vertices[] = new Vector2[numVertices];

        vertices[0] = new Vector2(0, 0);
        for (int i = 0; i < numVertices-1; i++) {
            double angle = (i / (float)(numVertices-2) * fieldAngle) - (90+fieldAngle/2);
            vertices[i+1] = new Vector2(
                    (float)(radius * Math.cos(Math.toRadians(angle))),
                    (float)(radius * Math.sin(Math.toRadians(angle)))
            );
        }
        polygonShape.set(vertices);

        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = PhysicalWorldHelper.CATEGORY_FIELD_OF_VISION;

        body.createFixture(fixtureDef);
    }

    public static void createSupportCallFixture(Body body){
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circle = new CircleShape();
        circle.setRadius(8);

        fixtureDef.shape = circle;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = PhysicalWorldHelper.CATEGORY_SUPPORT_CALL_FIELD;

        body.createFixture(fixtureDef);
    }

    public static void createCombatZoneFixture(Body body){
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circle = new CircleShape();
        circle.setRadius(3.5f);

        fixtureDef.shape = circle;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = PhysicalWorldHelper.CATEGORY_COMBAT_ZONE_FIELD;

        body.createFixture(fixtureDef);
    }
}
