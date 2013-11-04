package com.example.mylibgdxgame.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioPlayer {

    private static Music music;

    public static boolean finishedPlaying(){
        return !music.isPlaying();
    }

    public static void playMusic(){
        music.play();
    }

    public static void toggleMusic(){
        if (music.isPlaying()){
            pauseMusic();
        } else {
            playMusic();
        }
    }

    public static void pauseMusic(){
        music.pause();
    }

    public static void loadMusic(String filepath, boolean looping) {
        if (music != null){
            music.dispose();
        }

        music = Gdx.audio.newMusic(Gdx.files.internal(filepath));
        music.setLooping(looping);
    }

}
