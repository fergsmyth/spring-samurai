package com.genericgames.samurai.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.genericgames.samurai.model.Level;

import java.io.*;

public class GameIO {

    private static String APP_NAME = ".springsamurai";
    private static String VERSION = "0_0_1";
    private static String HOME = File.separator + APP_NAME + File.separator + VERSION + File.separator;
    private static String SAVE = HOME + "saves" + File.separator;

    public static Level loadGame(String levelName) {
        Level level = null;
        try {
            FileHandle fileHandle = Gdx.files.external(SAVE + levelName);
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

    public static void saveGame(Level level, String fileName){
        try {
            createSaveDirectory();
            File saveFile = Gdx.files.external(SAVE + fileName + Resource.SAVE_EXTENSION).file();
            OutputStream file = new FileOutputStream(saveFile);
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

    public static FileHandle[] getSaves(){
        FileHandle[] saves = Gdx.files.external(SAVE).list();
        return saves;
    }

    private static void createSaveDirectory() {
        File saveDirectory = Gdx.files.external(SAVE).file();
        if(!saveDirectory.exists()) {
            saveDirectory.mkdirs();
        }
    }

}
