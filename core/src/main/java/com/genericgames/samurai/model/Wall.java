package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.physics.PhysicalWorldFactory;

public class Wall extends WorldObject implements Collidable {

    public Wall(int positionX, int positionY, World world){
        super(positionX, positionY);
        body = PhysicalWorldFactory.createStaticPhysicalWorldObject(this, world);
        PhysicalWorldFactory.createRectangleFixture(body, 0.5f, 0.5f);
	}

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public String debugInfo() {
        return "Wall\nPos x: "+ getX() +"\nPos y : " + getY();
    }
}