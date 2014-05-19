package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.physics.PhysicalWorldFactory;
import com.genericgames.samurai.physics.PhysicalWorldHelper;

public class ImpassableGate extends WorldObject implements Collidable {

    public ImpassableGate(int positionX, int positionY, World world){
        super(positionX, positionY);
        body = PhysicalWorldFactory.createStaticPhysicalWorldObject(this, world);
        PhysicalWorldFactory.createRectangleFixture(body, 0.5f, 0.5f,
                PhysicalWorldHelper.CATEGORY_IMPASSABLE_GATE);
	}

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public String debugInfo() {
        return "ImpassableGate\nPos x: "+ getX() +"\nPos y : " + getY();
    }
}