package com.example.mylibgdxgame.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioPlayer {

    private static Music koto = loadMusic("music/KotoMusic.mp3");
//    private static Sound gong = loadSound("sound/gong.WAV");

    public static void playGong(){
//        gong.play();
    }

    public static void playMusic(){
        koto.play();
    }

    public static void toggleMusic(){
        if (koto.isPlaying()){
            pauseMusic();
        } else {
            playMusic();
        }
    }

    public static void pauseMusic(){
        koto.pause();
    }

    public static Sound loadSound(String filepath) {
        return Gdx.audio.newSound(Gdx.files.internal(filepath)) ;
    }

    public static Music loadMusic(String filepath) {
        Music musicFile = Gdx.audio.newMusic(Gdx.files.internal(filepath));
        musicFile.setLooping(true);
        return musicFile;
    }

}
