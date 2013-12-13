package com.genericgames.samurai.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.genericgames.samurai.model.Collidable;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.WorldObject;
import com.genericgames.samurai.model.movable.living.Living;
import com.genericgames.samurai.utility.CoordinateSystem;

public class PhysicalWorld {

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

	public static void createPhysicalCharacter(Living character, World physicalWorld, BodyType bodyType) {
		float bodyWidth = 0.35f;
		float bodyHeight = 0.35f;
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = bodyType;
		// Set our body's starting position in the world
		bodyDef.position.set(character.getPositionX(), character.getPositionY());

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

		body.createFixture(fixtureDef);

        createAttackFieldFixture(body, polygonShape);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		polygonShape.dispose();

		body.setUserData(character);
	}

    public static void createAttackFieldFixture(Body body, PolygonShape polygonShape) {
        polygonShape.setAsBox(0.35f, 0.1f, new Vector2(0f, -0.5f), 0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        fixtureDef.friction = 0f;

        body.createFixture(fixtureDef);
    }

    public static Fixture getAttackFieldFor(Living character, World world){
        for(Fixture fixture : getBodyFor(character, world).getFixtureList()){
            if(fixture.isSensor()){
                return fixture;
            }
        }
        throw new IllegalArgumentException("No sensor fixture was found for Living object: "+character+".");
    }

    public static void createPhysicalWorldObject(WorldObject worldObject, World physicalWorld) {
		float bodyWidth = 0.5f;
		float bodyHeight = 0.5f;
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.StaticBody;
		// Set our body's starting position in the world
		bodyDef.position.set(worldObject.getPositionX() + 0.5f, worldObject.getPositionY() + 0.5f);

		// Create our body in the world using our body definition
		Body body = physicalWorld.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(bodyWidth, bodyHeight);

		body.createFixture(polygonShape, 0.0f);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		polygonShape.dispose();

		body.setUserData(worldObject);
	}
}
