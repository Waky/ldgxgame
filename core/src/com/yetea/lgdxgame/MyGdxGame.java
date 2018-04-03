package com.yetea.lgdxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.yetea.lgdxgame.Screens.TitleScreen;

public class MyGdxGame extends Game {
	static public Skin gameSkin;
	
	@Override
	public void create () {
		gameSkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
		this.setScreen(new TitleScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		gameSkin.dispose();
	}
}
