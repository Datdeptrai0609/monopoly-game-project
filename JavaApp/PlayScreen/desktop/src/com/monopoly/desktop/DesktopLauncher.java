package com.monopoly.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.monopoly.MonopolyOOP;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 1800;
		config.height = 1000;
		config.resizable = false;

		new LwjglApplication(new MonopolyOOP(), config);
	}
}
