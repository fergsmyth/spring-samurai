package com.genericgames.samurai.model;

public class Door extends WorldObject implements Collidable {

    private String fileName;

    public Door(int positionX, int positionY){
        super(positionX, positionY);
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public String getFileName(){
        return fileName;
    }
}
