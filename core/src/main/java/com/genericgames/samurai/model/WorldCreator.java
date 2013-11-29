package com.genericgames.samurai.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.model.movable.living.Chest;
import com.genericgames.samurai.model.movable.living.Living;
import com.genericgames.samurai.physics.PhysicalWorld;

public class WorldCreator {

    public static World createPhysicalWorld(Level world){
        World physicalWorld = new World(new Vector2(0, 0), true);
        createWalls(world, physicalWorld);
        createDoors(world,physicalWorld);
        createChests(world, physicalWorld);
        createCharacters(world, physicalWorld);
        return physicalWorld;
    }

    private static void createCharacters(Level world, World physicalWorld){
        PhysicalWorld.createPhysicalCharacter(world.getPlayerCharacter(), physicalWorld, BodyDef.BodyType.DynamicBody);

        for(Living character : world.getEnemies()){
            PhysicalWorld.createPhysicalCharacter(character, physicalWorld, BodyDef.BodyType.KinematicBody);
        }
    }

    private static void createWalls(Level world, World physicalWorld) {
        for (Wall wall : world.getWalls()){
            PhysicalWorld.createPhysicalWorldObject(wall, physicalWorld);
        }
    }

    private static void createDoors(Level world, World physicalWorld){
        for( Door door : world.getDoors()){
            PhysicalWorld.createPhysicalWorldObject(door, physicalWorld);
        }
    }

    private static void createChests(Level world, World physicalWorld){
        for (Chest chest : world.getChests()){
            PhysicalWorld.createPhysicalWorldObject(chest, physicalWorld);
        }
    }
}
