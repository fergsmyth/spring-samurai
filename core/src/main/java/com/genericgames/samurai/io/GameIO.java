package com.genericgames.samurai.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.genericgames.samurai.model.Level;
import com.genericgames.samurai.scoreboard.Scoreboard;

import java.io.*;

public class GameIO {

    private static String APP_NAME = ".springsamurai";
    private static String VERSION = "0_0_1";
    private static String HOME = File.separator + APP_NAME + File.separator + VERSION + File.separator;
    private static String SAVE = HOME + "saves" + File.separator;
    private static final String SCOREBOARD = SAVE + "SCOREBOARD" + Resource.SCORE_EXTENSION;

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

    public static void saveScoreboard(Scoreboard scoreboard){
        createSaveDirectory();
        String scoreboardPath = SAVE + "SCOREBOARD" + Resource.SCORE_EXTENSION;
        Save saveGame = new Save();
        saveGame.setPath(scoreboardPath);
        saveGame.setObjectToSave(scoreboard);
        new Thread(saveGame).start();
    }

    public static void saveGame(Level level, String fileName){
            createSaveDirectory();
            String path = SAVE + fileName + Resource.SAVE_EXTENSION;
            Save saveGame = new Save();
            saveGame.setPath(path);
            saveGame.setObjectToSave(level);
            new Thread(saveGame).start();
    }

    public static FileHandle[] getSaves(){
        FileHandle[] saves = Gdx.files.external(SAVE).list();
        return saves;
    }

    public static Scoreboard getScoreboard(){
        Scoreboard scoreboard = null;
        try {
            FileHandle fileHandle = Gdx.files.external(SAVE + "SCOREBOARD" + Resource.SCORE_EXTENSION);
            File file = fileHandle.file();
            if (file.exists()) {
                InputStream stream = new FileInputStream(file);
                InputStream buffer = new BufferedInputStream(stream);
                ObjectInput input = new ObjectInputStream(buffer);
                scoreboard = (Scoreboard) input.readObject();
            } else {
                return new Scoreboard();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scoreboard;
    }

    private static void createSaveDirectory() {
        File saveDirectory = Gdx.files.external(SAVE).file();
        if(!saveDirectory.exists()) {
            saveDirectory.mkdirs();
        }
    }

}
