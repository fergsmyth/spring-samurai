package com.genericgames.samurai.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.genericgames.samurai.model.Collidable;
import com.genericgames.samurai.model.WorldObject;
import com.genericgames.samurai.utility.ImageCache;

public class Arrow extends WorldObject implements Collidable {

    public static final float RADIUS = 0.05f;
    public static final float SPEED = 1.5f;
    private static Sprite arrowTexture = new Sprite(new Texture(Gdx.files.internal("resources/image/Arrow.png")));


    public Arrow(float x, float y, Vector2 direction, World world){
        super(x, y, direction.getAngleRad());
        Vector2 distanceFromBody = distanceFromBody(direction);
        body = world.createBody(createBodyDef(x + distanceFromBody.x, y + distanceFromBody.y));
        createArrowFixture();
        body.setLinearVelocity(direction.nor().mulAdd(direction, SPEED));
        body.setTransform(body.getPosition(), direction.getAngleRad());
        body.setUserData(this);
    }

    private Vector2 distanceFromBody(Vector2 direction) {
        return direction.nor();
    }

    private BodyDef createBodyDef(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.bullet = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        return bodyDef;
    }

    private void createArrowFixture() {
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

    public void draw(SpriteBatch batch){
        float tileSize = ImageCache.tileSize;
        batch.draw(arrowTexture, getX() - 0.025f, getY() - 0.025f,
                0.5f, 0.5f, tileSize, tileSize, 1, 1, getRotationInDegrees()+90);
        System.out.println(debugInfo());
        //batch.draw(arrowTexture, getX() - 0.025f, getY() - 0.025f, 0.125f, 0.125f);
    }

    @Override
    public String debugInfo() {
        return "Arrow\nPos x: "+ getX() +"\nPos y : " + getY()+"\nRotation : " + getRotationInDegrees();
    }
}
