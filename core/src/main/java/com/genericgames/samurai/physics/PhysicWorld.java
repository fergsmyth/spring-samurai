package com.genericgames.samurai.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.model.Door;
import com.genericgames.samurai.model.Level;
import com.genericgames.samurai.model.Wall;
import com.genericgames.samurai.model.movable.living.Chest;
import com.genericgames.samurai.model.movable.living.Living;

public class PhysicWorld {

    public static World createPhysicWorld(Level level){
        World physicalWorld = new World(new Vector2(0, 0), true);
        createWalls(level, physicalWorld);
        createDoors(level,physicalWorld);
        createChests(level, physicalWorld);
        createCharacters(level, physicalWorld);
        return physicalWorld;
    }

    private static void createCharacters(Level world, World physicalWorld){
        PhysicalWorldFactory.createPlayer(world.getPlayerCharacter(), physicalWorld);

        for(Living character : world.getEnemies()){
            PhysicalWorldFactory.createEnemy(character, physicalWorld);
        }

        for(Living character : world.getNPCs()){
            PhysicalWorldFactory.createNPC(character, physicalWorld);
        }
    }

    private static void createWalls(Level world, World physicalWorld) {
        for (Wall wall : world.getWalls()){
            PhysicalWorldFactory.createPhysicalWorldObject(wall, physicalWorld, 0.5f, 0.5f);
        }
    }

    private static void createDoors(Level world, World physicalWorld){
        for( Door door : world.getDoors()){
            PhysicalWorldFactory.createPhysicalWorldObject(door, physicalWorld, 0.5f, 0.25f);
        }
    }

    private static void createChests(Level world, World physicalWorld){
        for (Chest chest : world.getChests()){
            PhysicalWorldFactory.createPhysicalWorldObject(chest, physicalWorld, 0.5f, 0.5f);
        }
    }
}
