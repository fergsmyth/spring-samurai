package com.genericgames.samurai.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.genericgames.samurai.model.Collidable;
import com.genericgames.samurai.model.WorldObject;

public class Arrow extends WorldObject implements Collidable {

    public static final float RADIUS = 0.05f;
    public static final float SPEED = 1.5f;
    private static Sprite arrowTexture = new Sprite(new Texture(Gdx.files.internal("resources/image/Arrow.png")));
    private Body body;

    public Arrow(float x, float y, Vector2 direction, World world){
        super(x, y);
        Vector2 distanceFromBody = distanceFromBody(direction);
        body = world.createBody(arrowBodyDef(x + distanceFromBody.x, y + distanceFromBody.y));
        createArrowFixture();
        body.setLinearVelocity(direction.nor().mulAdd(direction, SPEED));
        body.setUserData(this);
    }

    private Vector2 distanceFromBody(Vector2 direction) {
        return direction.nor().scl(0.20f);
    }

    private BodyDef arrowBodyDef(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.bullet = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(x, y);
        return bodyDef;
    }

    private void createArrowFixture() {
        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(RADIUS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0.5f;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.0f;
        fixtureDef.filter.categoryBits = PhysicalWorldHelper.CATEGORY_ARROW;

        body.createFixture(fixtureDef);
        circle.dispose();

    }

    @Override
    public void deleteBody(World world){
        if (body != null) {
            world.destroyBody(body);
            body = null;
        }
    }

    public void draw(SpriteBatch batch){
        arrowTexture.rotate(body.getAngle());
        batch.draw(arrowTexture, getX() - 0.025f, getY() - 0.025f, 0.125f, 0.125f);
    }
}
