package com.yetea.lgdxgame.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.yetea.lgdxgame.MyGdxGame;
import com.yetea.lgdxgame.ParallaxBackground;

public class GameScreen implements Screen {

    private Stage stage;
    private Game game;
    private int numTouches;


    public GameScreen(Game aGame){
        game = aGame;
        stage = new Stage(new ScreenViewport());

        numTouches = 0;

        Array<Texture> textures = new Array<Texture>();
        for (int i=1; i<=6;i++){
            textures.add(new Texture(Gdx.files.internal("parallax/img"+i+".png")));
            textures.get(textures.size-1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }

        final ParallaxBackground parallaxBackground = new ParallaxBackground(textures);
        parallaxBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        parallaxBackground.setSpeed(0);
        stage.addActor(parallaxBackground);

        TextButton backButton = new TextButton("Back", MyGdxGame.gameSkin);
        backButton.setWidth(300f);
        backButton.setHeight(150f);
        backButton.setPosition(Gdx.graphics.getWidth()/2-backButton.getWidth()/2,Gdx.graphics.getHeight()/4- backButton.getHeight()/2);
        backButton.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new TitleScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
        });

        TextButton plusSpeedButton = new TextButton("+",MyGdxGame.gameSkin);
        plusSpeedButton.setWidth(100f);
        plusSpeedButton.setHeight(100f);
        plusSpeedButton.setPosition(Gdx.graphics.getWidth()-150,50);
        plusSpeedButton.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                parallaxBackground.increaseSpeed();
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
        });

        TextButton lessSpeedButton = new TextButton("-",MyGdxGame.gameSkin);
        lessSpeedButton.setWidth(100f);
        lessSpeedButton.setHeight(100f);
        lessSpeedButton.setPosition(plusSpeedButton.getX()-150,50);
        lessSpeedButton.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                parallaxBackground.decreaseSpeed();
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
        });

        final Label numTouchesLabel = new Label("Score: "+numTouches,MyGdxGame.gameSkin);
        numTouchesLabel.setAlignment(Align.right);
        numTouchesLabel.setFontScale(3f);
        numTouchesLabel.setX(Gdx.graphics.getWidth()-200f);
        numTouchesLabel.setY(Gdx.graphics.getHeight()-50);
        numTouchesLabel.setColor(Color.BLACK);

        stage.addActor(numTouchesLabel);

        stage.addActor(backButton);
        stage.addActor(plusSpeedButton);
        stage.addActor(lessSpeedButton);

        stage.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                numTouches++;
                numTouchesLabel.setText("Score: "+numTouches);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
        });
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show");
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
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
