package com.genericgames.html;

import com.genericgames.core.SamuraiMain;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class SamuraiMainHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new SamuraiMain();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
