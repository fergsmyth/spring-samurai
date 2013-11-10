package com.genericgames.samurai.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.model.movable.living.Chest;
import com.genericgames.samurai.physics.PhysicalWorld;

public class WorldCreator {

    public static void createPhysicalWorld(MyWorld world){
        World physicalWorld = new World(new Vector2(0, 0), true);

        createWalls(world, physicalWorld);
        createDoors(world,physicalWorld);
        createChests(world, physicalWorld);
        createCharacter(world, physicalWorld);

        world.setPhysicalWorld(physicalWorld);
    }

    private static void createCharacter(MyWorld world, World physicalWorld){
        PhysicalWorld.createPhysicalPlayerCharacter(world.getPlayerCharacter(), physicalWorld);
    }

    private static void createWalls(MyWorld world, World physicalWorld) {
        for (Wall wall : world.getWalls()){
            PhysicalWorld.createPhysicalWorldObject(wall, physicalWorld);
        }
    }

    private static void createDoors(MyWorld world, World physicalWorld){
        for( Door door : world.getDoors()){
            PhysicalWorld.createPhysicalWorldObject(door, physicalWorld);
        }
    }

    private static void createChests(MyWorld world, World physicalWorld){
        for (Chest chest : world.getChests()){
            PhysicalWorld.createPhysicalWorldObject(chest, physicalWorld);
        }
    }
}
