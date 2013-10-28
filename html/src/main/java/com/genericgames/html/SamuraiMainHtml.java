package com.genericgames.html;

import com.example.mylibgdxgame.MyLibgdxGame;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class SamuraiMainHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new MyLibgdxGame();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
