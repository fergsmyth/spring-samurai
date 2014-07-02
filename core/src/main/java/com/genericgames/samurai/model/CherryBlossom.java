package com.genericgames.samurai.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.model.timeinterval.RandomTimeInterval;
import com.genericgames.samurai.physics.PhysicalWorldFactory;
import com.genericgames.samurai.physics.PhysicalWorldHelper;

import java.util.ArrayList;
import java.util.Collection;

public class CherryBlossom extends WorldObject implements Collidable {

    private Texture texture;
    private Collection<Emitter> petalEmitters;
    private float radius = 1.5f;

    public CherryBlossom(float positionX, float positionY, Wind wind, Texture texture, World world){
        super(positionX, positionY);
        this.texture = texture;
        body = PhysicalWorldFactory.createStaticPhysicalWorldObject(this, world);
        PhysicalWorldFactory.createCircularFixture(body, radius/3.0f,
                PhysicalWorldHelper.CATEGORY_INDESTRUCTIBLE);

        createPetalEmitters(wind);
    }

    private void createPetalEmitters(Wind wind) {
        petalEmitters = new ArrayList<Emitter>();
        Emitter<CherryBlossomPetal> petalEmitter = new Emitter<CherryBlossomPetal>(
                new CherryBlossomPetal.CherryBlossomPetalFactory(),
                getX(), getY(), wind.getDirection().cpy().scl(wind.getSpeed()), 45, true,
                new RandomTimeInterval(20, 40));
        Emitter<CherryBlossomPetal> petalEmitter2 = new Emitter<CherryBlossomPetal>(
                new CherryBlossomPetal.CherryBlossomPetalFactory(),
                getX()+radius, getY(), wind.getDirection().cpy().scl(wind.getSpeed()), 45, true,
                new RandomTimeInterval(20, 40));
        Emitter<CherryBlossomPetal> petalEmitter3 = new Emitter<CherryBlossomPetal>(
                new CherryBlossomPetal.CherryBlossomPetalFactory(),
                getX()-radius, getY(), wind.getDirection().cpy().scl(wind.getSpeed()), 45, true,
                new RandomTimeInterval(20, 40));
        petalEmitters.add(petalEmitter);
        petalEmitters.add(petalEmitter2);
        petalEmitters.add(petalEmitter3);
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        batch.begin();
        batch.draw(texture, getX()-radius, getY()-radius, 2*radius, 2*radius);
        batch.end();
    }

    @Override
    public String debugInfo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<Emitter> getPetalEmitters() {
        return petalEmitters;
    }

    public void setPetalEmitters(Collection<Emitter> petalEmitters) {
        this.petalEmitters = petalEmitters;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
