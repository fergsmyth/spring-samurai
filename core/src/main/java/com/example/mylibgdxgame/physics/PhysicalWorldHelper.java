package com.example.mylibgdxgame.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.example.mylibgdxgame.model.Collidable;
import com.example.mylibgdxgame.model.MyWorld;
import com.example.mylibgdxgame.model.Wall;
import com.example.mylibgdxgame.model.movable.living.playable.PlayerCharacter;
import com.example.mylibgdxgame.view.CoordinateSystem;

public class PhysicalWorldHelper {

    public static void checkForCollisions(MyWorld myWorld) {
		World physicalWorld = myWorld.getPhysicalWorld();

		// Step in physical world:
		physicalWorld.step(1 / 20f, 1, 1);


		Array<Body> bodies = new Array<Body>();
		physicalWorld.getBodies(bodies);

		for (Body b : bodies){
            Collidable c = (Collidable) b.getUserData();

			if (c != null) {
				// Correct the entities/sprites position and angle based on body's (potentially) new position
				c.setPosition(b.getPosition().x, b.getPosition().y);
                c.setRotation(b.getAngle());
			}
		}
	}

	public static void moveBody(World world, Collidable collidable, Vector2 direction, float velocityX, float velocityY){
		Body body = getBodyFor(collidable, world);
		body.setLinearVelocity(velocityX, velocityY);
        Vector2 mouseVector = CoordinateSystem.translateMousePosToWorldPosition(direction);
        body.setTransform(body.getPosition(), CoordinateSystem.getRotationAngleInRadians(mouseVector));
	}

    private static Body getBodyFor(Collidable collidable, World world) {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body b : bodies){

			// Get the bodies user data - in this example, our user
			// data is an instance of the Entity class
			Collidable c = (Collidable) b.getUserData();
			if(c == collidable){
				return b;
			}
		}
		throw new IllegalArgumentException("No matching body was found for Collidable: "+collidable+".");
	}

	public static void createPhysicalPlayerCharacter(PlayerCharacter playerCharacter, World physicalWorld) {
		float bodyWidth = 0.35f;
		float bodyHeight = 0.35f;
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(playerCharacter.getPositionX(), playerCharacter.getPositionY());
//		bodyDef.position.sub(new Vector2(bodyWidth, bodyHeight));

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

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		polygonShape.dispose();

		body.setUserData(playerCharacter);
	}

	public static void createPhysicalWall(Wall wall, World physicalWorld) {
		float bodyWidth = 0.5f;
		float bodyHeight = 0.5f;
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.StaticBody;
		// Set our body's starting position in the world
		bodyDef.position.set(wall.getPositionX(), wall.getPositionY());
//		bodyDef.position.sub(new Vector2(bodyWidth, bodyHeight));

		// Create our body in the world using our body definition
		Body body = physicalWorld.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(bodyWidth, bodyHeight);

		body.createFixture(polygonShape, 0.0f);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		polygonShape.dispose();

		body.setUserData(wall);
	}
}
