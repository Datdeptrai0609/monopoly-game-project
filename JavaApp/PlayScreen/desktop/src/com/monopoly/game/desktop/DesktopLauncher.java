package com.monopoly.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.monopoly.game.MonopolyGUI;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    config.setWindowedMode(1800, 1000);
    config.setResizable(false);
		new Lwjgl3Application(new MonopolyGUI(), config);
	}
}
