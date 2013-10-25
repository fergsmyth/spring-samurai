package com.example.mylibgdxgame.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AudioPlayer {

    private static Music koto = loadMusic();

    public static void playMusic(){
        if (koto == null){
            loadMusic();
        }
        koto.play();
    }

    public static void pauseMusic(){
        koto.pause();
    }

    public static Music loadMusic() {
        Music musicFile = Gdx.audio.newMusic(Gdx.files.internal("music/KotoMusic.mp3"));
        musicFile.setLooping(true);
        return musicFile;
    }

}
