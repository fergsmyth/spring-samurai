package com.genericgames.samurai.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.genericgames.samurai.model.Level;

import java.io.*;

public class GameIO {

    private static String APP_NAME = ".springsamurai";
    private static String SAVE = "saves";
    private static String VERSION = "0_0_1";
    private static String HOME = File.separator + APP_NAME + File.separator + VERSION + File.separator + SAVE + File.separator;

    public static Level loadGame(String levelName) {
        Level level = null;
        try {
            FileHandle fileHandle = Gdx.files.external(HOME + levelName);
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
            createDirectory();
            OutputStream file = new FileOutputStream(System.getProperty("user.home") + File.separator + HOME + "game1.ser");
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            try {
                output.writeObject(level);
            }
            finally {
                output.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private static void createDirectory() {
        File saveDirectory = Gdx.files.external(HOME).file();
        if(!saveDirectory.exists()) {
            saveDirectory.mkdirs();
        }
    }

}
