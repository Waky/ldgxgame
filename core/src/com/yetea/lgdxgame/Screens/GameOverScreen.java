package com.yetea.lgdxgame.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.yetea.lgdxgame.MyGdxGame;

public class GameOverScreen implements Screen {

    private Stage stage;
    private Game game;

    private Label gameOverLabel, scoreLabel;

    public GameOverScreen(Game aGame, int endScore){
        game = aGame;
        stage = new Stage(new ScreenViewport());

        gameOverLabel = new Label("Game Over!", MyGdxGame.gameSkin);
        gameOverLabel.setAlignment(Align.center);
        gameOverLabel.setFontScale(5f);
        gameOverLabel.setX(Gdx.graphics.getWidth()/2 - gameOverLabel.getWidth()/2);
        gameOverLabel.setY(500f);
        gameOverLabel.setColor(Color.WHITE);

        scoreLabel = new Label("Score: "+endScore, MyGdxGame.gameSkin);
        scoreLabel.setAlignment(Align.center);
        scoreLabel.setFontScale(5f);
        scoreLabel.setX(Gdx.graphics.getWidth()/2 - scoreLabel.getWidth()/2);
        scoreLabel.setY(gameOverLabel.getY() - 100);
        scoreLabel.setColor(Color.WHITE);

        TextButton playButton = new TextButton("Try Again!", MyGdxGame.gameSkin);
        playButton.setWidth(300f);
        playButton.setHeight(150f);
        playButton.setPosition(Gdx.graphics.getWidth()/2-playButton.getWidth()/2, 50f);
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new GameScreen(game));
            }

            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
        });

        stage.addActor(playButton);
        stage.addActor(gameOverLabel);
        stage.addActor(scoreLabel);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,1,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
