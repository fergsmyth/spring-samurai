package com.genericgames.samurai.levelloader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.genericgames.samurai.model.*;
import com.genericgames.samurai.model.movable.living.Chest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LevelLoader {

    public static void loadLevel(Level level) {
        FileHandle fileHandle = level.getFileHandle();
        InputStreamReader inputStreamReader = new InputStreamReader(fileHandle.read());
        BufferedReader br = new BufferedReader(inputStreamReader);
        try {
            //get the first line of the file
            String line = br.readLine();
            int lineNumber = 0;
            //while the line exists, execute the code within the curly brackets
            while(line != null) {
                char character;
                // Examine each character, one-by-one, of the line.
                // Define an integer (whole number), called 'i', with a value of zero,
                // that increments (adds one) until it's equal to the length of the line.
                for(int i=0; i<line.length(); i++){
                    character = line.charAt(i);

                    if(character == LevelLoaderLegend.WALL){
                        // create a wall
                        Wall wall = new Wall(i, lineNumber);
                        // add a new wall to the collection
                        level.addWall(wall);
//						PhysicalWorld.createPhysicalWorldObject(wall, physicalWorld);

                        // use the number of characters in the first line to record the level width:
                        if(lineNumber == 0){
                            level.incrementLevelWidth();
                        }
                    } else if(character == LevelLoaderLegend.DOOR){
                        Door door = new Door(i, lineNumber);
                        level.addDoor(door);
                        if(lineNumber == 0){
                            level.incrementLevelWidth();
                        }
                    } else if(character == LevelLoaderLegend.CHEST) {
                        Chest chest = new Chest(i, lineNumber);
                        level.addChest(chest);
                        if(lineNumber == 0){
                            level.incrementLevelWidth();
                        }
                    } else if(character == LevelLoaderLegend.ROOF){
                        Roof roof = new Roof(i, lineNumber);
                        level.addRoofTiles(roof);
                        if(lineNumber == 0){
                            level.incrementLevelWidth();
                        }
                    } else if(character == LevelLoaderLegend.START){
                        // put the Start
                        // playerCharacter at the START
                        level.getPlayerCharacter().setPosition(i, lineNumber);
                    }
                    else if(character == LevelLoaderLegend.END){
                        Castle castle = new Castle();
                        level.addCastle(castle);
                        // put the End
                        //castle at the End
                        castle.setPosition(i, lineNumber);
                    }
                }

                //get next line:
                line = br.readLine();
                //increase line number by one
                lineNumber = lineNumber + 1;
            }
            // use the number of lines in the file to record the level height:
            level.setLevelHeight(lineNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
