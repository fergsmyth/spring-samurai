package com.genericgames.samurai.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.genericgames.samurai.model.Level;

import java.io.*;

public class GameIO {

    public static Level loadGame() {
        Level level = null;
        try {
            FileHandle fileHandle = Gdx.files.external("springsamurai/save/game1.ser");
            File file = fileHandle.file();
            InputStream stream = new FileInputStream(file);
            InputStream buffer = new BufferedInputStream(stream);
            ObjectInput input = new ObjectInputStream(buffer);
            level = (Level) input.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return level;
    }

    public static void saveGame(Level level){
        try {
            FileHandle fileHandle = Gdx.files.external("springsamurai/save/game1.ser");
            File file = fileHandle.file();
            OutputStream stream = new FileOutputStream(file);
            OutputStream buffer = new BufferedOutputStream(stream);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(level);
            output.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

}
