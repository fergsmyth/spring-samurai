package com.genericgames.samurai.io;

import com.badlogic.gdx.Gdx;
import com.genericgames.samurai.model.Level;

import java.io.*;

public class Save implements Runnable {

    private String filePath;
    private Object objectToSave;

    @Override
    public void run() {
        try {
            File saveFile = Gdx.files.external(filePath).file();
            OutputStream file = new FileOutputStream(saveFile);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            try {
                output.writeObject(objectToSave);
            }
            finally {
                output.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setObjectToSave(Serializable object){
        this.objectToSave = object;
    }

    public void setPath(String fileName){
        this.filePath = fileName;
    }
}
