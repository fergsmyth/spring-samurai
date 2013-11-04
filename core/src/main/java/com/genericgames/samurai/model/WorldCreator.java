package com.genericgames.samurai.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.physics.PhysicalWorld;

public class WorldCreator {

    public static void createPhysicalWorld(MyWorld world){
        World physicalWorld = new World(new Vector2(0, 0), true);
        createWalls(world, physicalWorld);
        createCharacter(world, physicalWorld);
        world.setPhysicalWorld(physicalWorld);
    }

    private static void createCharacter(MyWorld world, World physicalWorld){
        PhysicalWorld.createPhysicalPlayerCharacter(world.getPlayerCharacter(), physicalWorld);
    }

    private static void createWalls(MyWorld world, World physicalWorld) {
        for (Wall wall : world.getWalls()){
            PhysicalWorld.createPhysicalWall(wall, physicalWorld);
        }
    }

}
