package com.example.mylibgdxgame.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.example.mylibgdxgame.levelloader.LevelLoaderLegend;
import com.example.mylibgdxgame.model.movable.living.playable.King;

public class MyWorld {

    private King king;
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
		king = new King();

        walls = new ArrayList<Wall>();
        castle = new Castle();
        loadLevel(Gdx.files.local("level.txt"));
    }

	private void createPhysicalKing(King king) {
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(king.getPositionX(), king.getPositionY());

		// Create our body in the world using our body definition
		Body kingBody = physicalWorld.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(0.25f, 0.25f);

		kingBody.createFixture(polygonShape, 0.0f);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		polygonShape.dispose();

		kingBody.setUserData(king);
	}

	private void createPhysicalWall(Wall wall) {
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.StaticBody;
		// Set our body's starting position in the world
		bodyDef.position.set(wall.getPositionX(), wall.getPositionY());

		// Create our body in the world using our body definition
		Body body = physicalWorld.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(0.5f, 0.5f);

		body.createFixture(polygonShape, 0.0f);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		polygonShape.dispose();

		body.setUserData(wall);
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
						createPhysicalWall(wall);

                        // use the number of characters in the first line to record the level width:
                        if(lineNumber == 0){
                            levelWidth = i+1;
                        }
                    }
                    else if(character == LevelLoaderLegend.START){
                        // put the Start
                        // king at the START
                        king.setPosition(i, lineNumber);
						createPhysicalKing(king);
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

    public King getKing() {
        return king;
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
