package com.genericgames.samurai.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import com.genericgames.samurai.model.WorldObject;

public class Arrow extends WorldObject {

    private static Texture arrowTexture = new Texture(Gdx.files.internal("resources/image/dot.png"));

    private Body body;

    public Arrow(float x, float y, World world){
        body = world.createBody(arrowBodyDef(x, y));
        createArrowFixture();
        body.setUserData(this);

    }

    public Body getBody(){
        return body;
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
        circle.setRadius(1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0f;
        fixtureDef.friction = 0f;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.0f;
        fixtureDef.filter.categoryBits = PhysicalWorldHelper.CATEGORY_ARROW;

        body.createFixture(fixtureDef);
        circle.dispose();

    }

}
