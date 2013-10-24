package com.example.mylibgdxgame.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.example.mylibgdxgame.model.Collidable;
import com.example.mylibgdxgame.model.MyWorld;

public class PhysicalWorldHelper {

	public static void checkForCollisions(MyWorld myWorld) {
		World physicalWorld = myWorld.getPhysicalWorld();

		// Step in physical world:
		physicalWorld.step(1 / 20f, 8, 3);


		Array<Body> bodies = new Array<Body>();
		physicalWorld.getBodies(bodies);

		for (Body b : bodies){

			// Get the bodies user data - in this example, our user
			// data is an instance of the Collidable class
			Collidable c = (Collidable) b.getUserData();

			if (c != null) {
				// Correct the entities/sprites position and angle based on body's (potentially) new position
				c.setPosition(b.getPosition().x, b.getPosition().y);
//				// We need to convert our angle from radians to degrees
//				c.setRotation(MathUtils.radiansToDegrees * b.getAngle());
			}
		}
	}

	public static void moveBody(World world, Collidable collidable, float velocityX, float velocitY){
		Body body = getBodyFor(collidable, world);
		body.setLinearVelocity(velocityX, velocitY);
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
}
