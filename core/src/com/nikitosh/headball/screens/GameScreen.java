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
import com.nikitosh.headball.ButtonsInputController;
import com.nikitosh.headball.InputController;
import com.nikitosh.headball.TouchpadInputController;
import com.nikitosh.headball.players.LocalHumanPlayer;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.GameWorld;
import com.nikitosh.headball.players.Player;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.utils.GameSettings;

public abstract class GameScreen implements Screen {

    protected final Game game;

    protected GameWorld gameWorld;
    protected Player[] players;

    protected Stage stage;
    protected Table pauseButtonTable;
    protected Table hudTable;

    protected InputController inputController;

    private Label scoreLabel;
    private Label timerLabel;

    protected enum GameState {GAME_RUNNING, GAME_PAUSED, GAME_OVER};
    protected GameState gameState = GameState.GAME_RUNNING;

    protected Window pauseScreen;
    protected GameOverScreen gameOverScreen;

    protected int playerNumber;
    protected float gameDuration = 0;

    public GameScreen(Game newGame) {
        game = newGame;

        gameWorld = new GameWorld();
        stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));

        Image background = new Image(AssetLoader.backgroundTexture);
        background.setFillParent(true);
        stage.addActor(background);

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
        pauseButtonTable.add(pauseButton).top().right().pad(Constants.UI_ELEMENTS_INDENT).row();

        scoreLabel = new Label("", AssetLoader.gameLabelStyle);
        timerLabel = new Label("", AssetLoader.gameLabelStyle);

        hudTable = new Table();
        hudTable.setFillParent(true);
        hudTable.add(timerLabel).pad(Constants.UI_ELEMENTS_INDENT).row();
        hudTable.add(scoreLabel).pad(Constants.UI_ELEMENTS_INDENT).row();

        if (GameSettings.getString("control").equals("Buttons")) {
            inputController = new ButtonsInputController();
        }
        else {
            inputController = new TouchpadInputController();
        }

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top();
        mainTable.add(gameWorld.getGroup()).top().expand().fillX().height(Constants.FIELD_HEIGHT);
        mainTable.bottom();
        mainTable.add(inputController.getTable()).bottom().fill().expand().row();
        Gdx.input.setInputProcessor(stage);

        players = new Player[2];

        Stack stack = new Stack();
        stack.setFillParent(true);

        stack.addActor(mainTable);
        stack.addActor(pauseButtonTable);
        stack.addActor(hudTable);
        stage.addActor(stack);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (gameWorld.isGoal() && GameSettings.getBoolean("sound")) {
            AssetLoader.goalSound.play();
        }
        if (gameWorld.isEnded()) {
            finishGame();
        }
        updateHUD();
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

    protected void initializePlayers() {
        players[playerNumber] = new LocalHumanPlayer(inputController);
    }


    public void finishGame() {
        stage.addActor(gameOverScreen);
        gameOverScreen.updateResult();
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

    public int[] getScore() {
        return gameWorld.getScore();
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    private void updateHUD() {
        int[] score = gameWorld.getScore();
        scoreLabel.setText(Integer.toString(score[0]) + " : "+ Integer.toString(score[1]));
        timerLabel.setText(String.format("%02d", Constants.GAME_DURATION - (int) gameWorld.getGameDuration()));
    }
}
