package com.jmr.terraria.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jmr.terraria.PhysicsGame;
import com.jmr.terraria.engine.utils.Settings;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		PhysicsGame.instantiateSettings();
		config.width = Settings.getWidth();
		config.height = Settings.getHeight();
		new LwjglApplication(new com.jmr.terraria.PhysicsGame(), config);
	}
}
