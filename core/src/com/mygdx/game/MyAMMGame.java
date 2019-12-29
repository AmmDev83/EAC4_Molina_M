package com.mygdx.game;

import com.badlogic.gdx.Game;

import helpers.AssetManager;
import screens.GameScreen;
import screens.SplashScreen;


public class MyAMMGame extends Game {


	@Override
	public void create() {

        // A l'iniciar el joc carreguem els recursos
        AssetManager.load();
        // I definim la pantalla d'splash com a pantalla
        setScreen(new SplashScreen(this));

	}

	// Cridem per descartar els recursos carregats.
	@Override
	public void dispose() {
		super.dispose();
		AssetManager.dispose();
	}
}
