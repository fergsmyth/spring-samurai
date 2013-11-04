package com.genericgames.samurai.model;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 01/10/13
 * Time: 19:17
 * To change this template use File | Settings | File Templates.
 */
public class Wall extends WorldObject implements Collidable {

    public Wall(int positionX, int positionY){
        super(positionX, positionY);
	}
}