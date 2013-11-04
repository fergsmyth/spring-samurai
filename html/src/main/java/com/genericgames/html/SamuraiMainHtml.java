package com.genericgames.html;

import com.genericgames.samurai.SpringSamuraiGame;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class SamuraiMainHtml extends GwtApplication {

	@Override
	public ApplicationListener getApplicationListener () {
		return new SpringSamuraiGame();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
