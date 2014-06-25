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

    public static Body createPlayer(WorldCharacter character, World physicalWorld) {
        Body body = createPhysicalCharacter(character, physicalWorld, BodyDef.BodyType.DynamicBody,
                PhysicalWorldHelper.CATEGORY_LIVING_BODY, PhysicalWorldHelper.MASK_OTHER);

        createLightFrontalAttackFieldFixture(body);
        createHeavySpinningAttackFieldFixture(body);
        return body;
    }

    public static Body createEnemy(WorldCharacter character, World physicalWorld) {
        Body body = createPhysicalCharacter(character, physicalWorld, BodyDef.BodyType.DynamicBody,
                PhysicalWorldHelper.CATEGORY_LIVING_BODY, PhysicalWorldHelper.MASK_AI);

        createLightFrontalAttackFieldFixture(body);
        createHeavyFrontalAttackFieldFixture(body);
        createFieldOfVisionFixture(body);
        return body;
    }

    public static Body createNPC(WorldCharacter character, World physicalWorld) {
        Body body = createPhysicalCharacter(character, physicalWorld, BodyType.StaticBody,
                PhysicalWorldHelper.CATEGORY_NPC_BODY, PhysicalWorldHelper.MASK_AI);
        createConversationFixture(body);
        return body;
    }

    private static Body createPhysicalCharacter(WorldCharacter character, World physicalWorld, BodyType type,
                                                short livingBodyCategory, short characterMask) {
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
        fixtureDef.filter.maskBits = characterMask;

		body.createFixture(fixtureDef);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		polygonShape.dispose();

		body.setUserData(character);

        return body;
	}

    public static Body createStaticPhysicalWorldObject(WorldObject worldObject, World physicalWorld) {

        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyType.StaticBody;
        // Set our body's starting position in the world
        bodyDef.position.set(worldObject.getX() + 0.5f, worldObject.getY() + 0.5f);

        // Create our body in the world using our body definition
        Body body = physicalWorld.createBody(bodyDef);
        body.setUserData(worldObject);
        return body;
    }

    public static void createRectangleFixture(Body body, float width, float height, short category){
        // Create a circle shape and set its radius to 6
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width, height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = false;
        fixtureDef.friction = 0f;
        fixtureDef.filter.categoryBits = category;

        body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        polygonShape.dispose();
    }

    public static void createPhysicalWorldObject(WorldObject worldObject, World physicalWorld,
                                                 float bodyWidth, float bodyHeight, short category) {

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
        fixtureDef.filter.categoryBits = category;

        body.createFixture(fixtureDef);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		polygonShape.dispose();

		body.setUserData(worldObject);
	}

    public static void createLightFrontalAttackFieldFixture(Body body) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.35f, 0.1f, new Vector2(0f, -0.5f), 0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.friction = 0f;
        fixtureDef.filter.categoryBits = PhysicalWorldHelper.CATEGORY_LIGHT_ATTACK_FIELD;

        body.createFixture(fixtureDef);
    }

    public static void createHeavyFrontalAttackFieldFixture(Body body) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.35f, 0.1f, new Vector2(0f, -0.5f), 0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.friction = 0f;
        fixtureDef.filter.categoryBits = PhysicalWorldHelper.CATEGORY_HEAVY_ATTACK_FIELD;

        body.createFixture(fixtureDef);
    }

    public static void createHeavySpinningAttackFieldFixture(Body body) {
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circle = new CircleShape();
        circle.setRadius(1.5f);

        fixtureDef.shape = circle;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = PhysicalWorldHelper.CATEGORY_HEAVY_ATTACK_FIELD;

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
}
