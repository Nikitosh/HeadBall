package com.nikitosh.headball.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nikitosh.headball.HeadballGame;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.GameSettings;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 960;
		config.height = 540;
		new LwjglApplication(new HeadballGame(), config);
		GameSettings.putString(Constants.SETTINGS_CONTROL, Constants.SETTINGS_CONTROL_KEYBOARD);
	}
}
