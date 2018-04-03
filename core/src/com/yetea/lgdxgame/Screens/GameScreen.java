package com.yetea.lgdxgame.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.yetea.lgdxgame.Barrier;
import com.yetea.lgdxgame.MyGdxGame;
import com.yetea.lgdxgame.ParallaxBackground;
import com.yetea.lgdxgame.Player;

import java.util.Random;

public class GameScreen implements Screen {

    private Stage stage;
    private Stage UIstage;
    private Game game;
    private int score;
    private float deltaForScore;
    private Label scoreLabel;
    private Player player;

    private final float PLAYER_X = 50f;

    private float deltaForBarriers;
    private Barrier testBarrier;
    private Barrier[] barriers;

    private Random mRandom;

    private InputMultiplexer multiplexer;

    public GameScreen(Game aGame){
        game = aGame;
        stage = new Stage(new ScreenViewport());
        UIstage = new Stage(new ScreenViewport());

        score = 0;
        deltaForScore = 0;

        barriers = new Barrier[5];
        deltaForBarriers = 0;

        Array<Texture> textures = new Array<Texture>();
        for (int i=1; i<=6;i++){
            textures.add(new Texture(Gdx.files.internal("parallax/img"+i+".png")));
            textures.get(textures.size-1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }

        final ParallaxBackground parallaxBackground = new ParallaxBackground(textures);
        parallaxBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        parallaxBackground.setSpeed(0.5f);
        stage.addActor(parallaxBackground);
        parallaxBackground.setZIndex(0);

        mRandom = new Random();
        for (int i=0;i<barriers.length; i++){
            barriers[i] = new Barrier(stage, mRandom.nextInt(20)+40);
            barriers[i].setX(Gdx.graphics.getWidth()+(Gdx.graphics.getWidth()/2*i));
            barriers[i].setZIndex(5);
        }

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
                //parallaxBackground.increaseSpeed();
                for (Barrier barrier : barriers) barrier.incSpeed();
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
                //parallaxBackground.decreaseSpeed();
                for (Barrier barrier : barriers) barrier.decSpeed();
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
        });

        scoreLabel = new Label("Score: "+score,MyGdxGame.gameSkin);
        scoreLabel.setAlignment(Align.right);
        scoreLabel.setFontScale(3f);
        scoreLabel.setX(Gdx.graphics.getWidth()-200f);
        scoreLabel.setY(Gdx.graphics.getHeight()-50f);
        scoreLabel.setColor(Color.BLACK);

        Texture airballoontex = new Texture(Gdx.files.internal("hot_air_balloon_andy.png"));
        player = new Player(airballoontex);
        player.setSize(150,225);
        player.setPosition(PLAYER_X,Gdx.graphics.getHeight()/2-player.getHeight());

        //testBarrier = new Barrier(stage, 40);

        stage.addActor(scoreLabel);
        UIstage.addActor(backButton);
        UIstage.addActor(plusSpeedButton);
        UIstage.addActor(lessSpeedButton);
        stage.addActor(player);

        stage.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                player.startFall();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                player.startRise();
                return true;
            }
        });

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(UIstage);
        multiplexer.addProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show");
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        UIstage.act();

        stage.draw();
        UIstage.draw();

        if (player.getY() < (0-(player.getHeight()*player.getScaleY()))){
            //GAME OVER
            score = 0;
            scoreLabel.setText("Score: "+score);
            player.addAction(Actions.moveTo(PLAYER_X, Gdx.graphics.getHeight()/2-player.getHeight(),0.8f));
            deltaForScore = 0;
        } else if (player.getY() > Gdx.graphics.getHeight()){
            player.startFall();
        }

        for (int i=0;i<barriers.length;i++){
            barriers[i].update();
            if (barriers[i].collidesWith(player)){
                score = 0;
                scoreLabel.setText("Score: "+score);
            }
            if (barriers[i].getX() < PLAYER_X){
                if (!barriers[i].hasScored())
                    score++;
                    scoreLabel.setText("Score: "+score);
                    barriers[i].yesScore();
            }
            if (barriers[i].getX() <= -50f){
                if (i == 0) {
                    barriers[i].setX(barriers[4].getX() + Gdx.graphics.getWidth() / 2);
                    barriers[i].noScore();
                } else {
                    barriers[i].setX(barriers[i - 1].getX() + Gdx.graphics.getWidth() / 2);
                    barriers[i].noScore();
                }
            }
        }
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
        UIstage.dispose();
    }
}
