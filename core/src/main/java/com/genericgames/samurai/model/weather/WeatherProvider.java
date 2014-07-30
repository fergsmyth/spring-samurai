package com.genericgames.samurai.model.weather;

import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.audio.SoundEffectCache;
import com.genericgames.samurai.maths.RandomPointFromRect;
import com.genericgames.samurai.model.Level;
import com.genericgames.samurai.model.RandomSpaceEmitter;
import com.genericgames.samurai.model.timeinterval.FixedTimeInterval;

public class WeatherProvider {

    public static RandomSpaceEmitter getWeatherEmitter(Weather weather, Level level){
        RandomSpaceEmitter correspondingWeatherEmitter;
        Vector2 emitterPosition = WeatherHelper.getWeatherEmitterPosition(level);

        SoundEffectCache.stopAllWeatherEffectSounds();

        switch (weather){
            case RAIN:
                correspondingWeatherEmitter = new RandomSpaceEmitter(
                        new RainDrop.RainDropFactory(),
                        emitterPosition.x, emitterPosition.y,
                        level.getWind().getDirection().cpy().scl(level.getWind().getSpeed()*WeatherHelper.RAIN_SPEED_SCALAR),
                        15, true, new FixedTimeInterval(1), 10,
                        new RandomPointFromRect(WeatherHelper.getWeatherEmitterSpace(level)));
                SoundEffectCache.playRainSound();
                break;
            case SNOW:
                correspondingWeatherEmitter = new RandomSpaceEmitter(
                        new SnowFlake.SnowFlakeFactory(),
                        emitterPosition.x, emitterPosition.y,
                        level.getWind().getDirection().cpy().scl(level.getWind().getSpeed()*WeatherHelper.SNOW_SPEED_SCALAR),
                        45, true, new FixedTimeInterval(1), 5,
                        new RandomPointFromRect(WeatherHelper.getWeatherEmitterSpace(level)));
                SoundEffectCache.playSnowSound();
                break;
            default:
                correspondingWeatherEmitter = null;
                break;
        }
        return correspondingWeatherEmitter;
    }
}
