package com.genericgames.samurai.model.weather;

import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.maths.RandomPointFromRect;
import com.genericgames.samurai.model.Level;
import com.genericgames.samurai.model.RandomSpaceEmitter;
import com.genericgames.samurai.model.timeinterval.FixedTimeInterval;

public class WeatherProvider {

    public static RandomSpaceEmitter getWeatherEmitter(Weather weather, Level level){
        RandomSpaceEmitter correspondingWeatherEmitter;
        Vector2 emitterPosition = WeatherHelper.getWeatherEmitterPosition(level);

        switch (weather){
            case RAIN:
                correspondingWeatherEmitter = new RandomSpaceEmitter(
                        new RainDrop.RainDropFactory(),
                        emitterPosition.x, emitterPosition.y,
                        level.getWind().getDirection().cpy().scl(level.getWind().getSpeed()*WeatherHelper.RAIN_SPEED_SCALAR),
                        15, true, new FixedTimeInterval(1),
                        new RandomPointFromRect(WeatherHelper.getWeatherEmitterSpace(level)));
                break;
            case SNOW:
                correspondingWeatherEmitter = new RandomSpaceEmitter(
                        new SnowFlake.SnowFlakeFactory(),
                        emitterPosition.x, emitterPosition.y,
                        level.getWind().getDirection().cpy().scl(level.getWind().getSpeed()*WeatherHelper.SNOW_SPEED_SCALAR),
                        45, true, new FixedTimeInterval(1),
                        new RandomPointFromRect(WeatherHelper.getWeatherEmitterSpace(level)));
                break;
            default:
                correspondingWeatherEmitter = null;
                break;
        }
        return correspondingWeatherEmitter;
    }
}
