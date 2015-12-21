package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.GameWorld;
import com.nikitosh.headball.players.Player;
import com.nikitosh.headball.ui.GameTextButtonTouchable;

public abstract class GameScreen implements Screen {

    protected final Game game;

    protected GameWorld gameWorld;
    protected Player[] players;
    protected Stage stage;
    protected Table uiTable;
    protected Table gameplayTable;
    protected Table pauseButtonTable;
    protected GameTextButtonTouchable hitButton, jumpButton, leftButton, rightButton;

    protected enum GameState {GAME_RUNNING, GAME_PAUSED, GAME_OVER};
    protected GameState gameState = GameState.GAME_RUNNING;
    protected Label resultLabel;

    protected Window pauseScreen;
    protected Window gameOverScreen;

    protected float gameDuration = 0;

    public GameScreen(Game newGame) {
        game = newGame;

        gameWorld = new GameWorld();
        stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));

        uiTable = new Table();
        //uiTable.setFillParent(true);

        Image background = new Image(AssetLoader.backgroundTexture);
        background.setFillParent(true);
        stage.addActor(background);

        hitButton = new GameTextButtonTouchable("Hit");
        jumpButton = new GameTextButtonTouchable("Jump");
        leftButton = new GameTextButtonTouchable("Left");
        rightButton = new GameTextButtonTouchable("Right");

        uiTable.add(hitButton).expand().fillX().bottom();
        uiTable.add(jumpButton).expand().fillX().bottom();
        uiTable.add(leftButton).expand().fillX().bottom();
        uiTable.add(rightButton).expand().fillX().bottom();

        pauseScreen = new PauseScreen(this);
        pauseScreen.setBounds(Constants.VIRTUAL_WIDTH / 4,
                Constants.VIRTUAL_HEIGHT / 4,
                Constants.VIRTUAL_WIDTH / 2,
                Constants.VIRTUAL_HEIGHT / 2);

        gameOverScreen = new GameOverScreen(this);
        gameOverScreen.setBounds(Constants.VIRTUAL_WIDTH / 4,
                Constants.VIRTUAL_HEIGHT / 4,
                Constants.VIRTUAL_WIDTH / 2,
                Constants.VIRTUAL_HEIGHT / 2);

        pauseButtonTable = new Table();
        pauseButtonTable.setFillParent(true);

        GameTextButtonTouchable pauseButton = new GameTextButtonTouchable("Pause");
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseGame();
            }
        });

        pauseButtonTable.top().right();
        pauseButtonTable.add(pauseButton).top().right().pad(Constants.BUTTON_INDENT).row();

        Gdx.input.setInputProcessor(stage);

        players = new Player[2];

        Stack stack = new Stack();
        stack.setFillParent(true);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top();
        mainTable.add(gameWorld.getGroup()).top().expand().fillX().height(Constants.FIELD_HEIGHT);
        mainTable.bottom();
        mainTable.add(uiTable).bottom().fill().expand();

        stack.addActor(mainTable);
        stack.addActor(pauseButtonTable);
        Gdx.app.log("HEY", Float.toString(mainTable.getY()));
        stage.addActor(stack);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (gameState == GameState.GAME_RUNNING) {
            gameDuration += delta;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        pauseGame();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gameWorld.getBox2dWorld().dispose();
        stage.dispose();
    }

    protected abstract void initializePlayers();

    public void finishGame() {
        stage.addActor(gameOverScreen);
        gameState = GameState.GAME_OVER;

    }

    public void pauseGame() {
        stage.addActor(pauseScreen);
        gameState = GameState.GAME_PAUSED;
    }

    public void resumeGame() {
        gameState = GameState.GAME_RUNNING;
        pauseScreen.remove();
    }

    public void restartGame() {
    }

    public void exitGame() {
        dispose();
        game.setScreen(new MenuScreen(game));
    }
}
