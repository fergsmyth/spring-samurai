package com.example.mylibgdxgame.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.example.mylibgdxgame.levelloader.LevelLoaderLegend;
import com.example.mylibgdxgame.model.movable.living.playable.PlayerCharacter;
import com.example.mylibgdxgame.physics.PhysicalWorldHelper;

public class MyWorld {

    private PlayerCharacter playerCharacter;
    private Collection<Wall> walls;
    private Castle castle;

    private int levelHeight;
    private int levelWidth;

	//For physics and collision detection:
	private World physicalWorld = new World(new Vector2(0, 0), true);

    public MyWorld() {
        createDemoWorld();
    }

    private void createDemoWorld() {
		playerCharacter = new PlayerCharacter();

        walls = new ArrayList<Wall>();
        castle = new Castle();
        loadLevel(Gdx.files.local("level.txt"));
    }

	private void loadLevel(FileHandle fileHandle) {
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
                        walls.add(wall);
						PhysicalWorldHelper.createPhysicalWall(wall, physicalWorld);

                        // use the number of characters in the first line to record the level width:
                        if(lineNumber == 0){
                            levelWidth = i+1;
                        }
                    }
                    else if(character == LevelLoaderLegend.START){
                        // put the Start
                        // playerCharacter at the START
                        playerCharacter.setPosition(i, lineNumber);
						PhysicalWorldHelper.createPhysicalPlayerCharacter(playerCharacter, physicalWorld);
                    }
                    else if(character == LevelLoaderLegend.END){
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
            levelHeight = lineNumber;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PlayerCharacter getPlayerCharacter() {
        return playerCharacter;
    }

    public Castle getCastle() {
        return castle;
    }

    public Collection<Wall> getWalls() {
        return walls;
    }

    public int getLevelWidth() {
        return levelWidth;
    }

    public int getLevelHeight() {
        return levelHeight;
    }

	public World getPhysicalWorld() {
		return physicalWorld;
	}

}
