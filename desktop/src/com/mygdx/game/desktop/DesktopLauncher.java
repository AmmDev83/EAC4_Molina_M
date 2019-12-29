package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyAMMGame;

import utils.Settings;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Nit de Monstres";
		config.width = Settings.GAME_WIDTH * 2;
		config.height = Settings.GAME_HEIGHT * 2;
		new LwjglApplication(new MyAMMGame(), config);
	}
}
