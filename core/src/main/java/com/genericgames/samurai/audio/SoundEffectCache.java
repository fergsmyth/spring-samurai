package com.genericgames.samurai.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundEffectCache {

    public static Sound swordSlash;
    public static Sound swordClash;
    public static Sound swordDraw;
    public static Sound bow;
    public static Sound grunt1;
    public static Sound grunt2;
    public static Sound pickupQuiver;

    public static Sound rain;
    public static long rainID;
//    public static Sound snow;
//    public static long snowID;

    public static void load () {
        swordSlash = Gdx.audio.newSound(Gdx.files.internal("audio/sound/sword_slash.wav"));
        swordClash = Gdx.audio.newSound(Gdx.files.internal("audio/sound/sword_clash.wav"));
        swordDraw = Gdx.audio.newSound(Gdx.files.internal("audio/sound/sword_clash.wav"));
        bow = Gdx.audio.newSound(Gdx.files.internal("audio/sound/bow.wav"));
        grunt1 = Gdx.audio.newSound(Gdx.files.internal("audio/sound/grunt1.wav"));
        grunt2 = Gdx.audio.newSound(Gdx.files.internal("audio/sound/grunt2.wav"));
        pickupQuiver = Gdx.audio.newSound(Gdx.files.internal("audio/sound/pickup.wav"));

        rain = Gdx.audio.newSound(Gdx.files.internal("audio/sound/rain2.wav"));
    }

    public static void stopAllWeatherEffectSounds() {
        rain.stop(rainID);             // stops the looping sound
//        snow.stop(snowID);             // stops the looping sound
    }

    public static void playRainSound(){
        rainID = SoundEffectCache.rain.play(1.0f);
        SoundEffectCache.rain.setLooping(rainID, true);
    }

    public static void playSnowSound() {
//        snowID = SoundEffectCache.snow.play(1.0f);
//        SoundEffectCache.snow.setLooping(snowID, true);
    }
}
