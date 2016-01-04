package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.controllers.ButtonsInputController;
import com.nikitosh.headball.controllers.InputController;
import com.nikitosh.headball.controllers.TouchpadInputController;
import com.nikitosh.headball.players.LocalHumanPlayer;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.GameWorld;
import com.nikitosh.headball.players.Player;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.utils.GameSettings;

public abstract class GameScreen implements Screen {

    protected final Game game;
    protected Screen previousScreen;
    protected Team firstTeam;
    protected Team secondTeam;

    protected GameWorld gameWorld;
    protected Player[] players;

    protected Stage stage;
    protected Table pauseButtonTable;
    protected InputController inputController;

    private Label scoreLabel;
    private Label timerLabel;

    protected enum GameState {GAME_RUNNING, GAME_PAUSED, GAME_OVER};
    protected GameState gameState = GameState.GAME_RUNNING;

    protected Window pauseScreen;
    protected GameOverScreen gameOverScreen;

    protected int playerNumber;

    public GameScreen(Game newGame, Team firstTeam, Team secondTeam, Screen previousScreen) {
        game = newGame;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.previousScreen = previousScreen;

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

        pauseButtonTable.add(pauseButton).top().right().expand().pad(Constants.UI_ELEMENTS_INDENT).row();

        Table infoTable = new Table();
        Table nestedTable = new Table();
        timerLabel = new Label("", AssetLoader.defaultSkin);
        scoreLabel = new Label("", AssetLoader.defaultSkin);
        timerLabel.setAlignment(Align.center);
        scoreLabel.setAlignment(Align.center);
        nestedTable.add(timerLabel).fillX().row();
        nestedTable.add(scoreLabel).fillX();
        infoTable.add(new Label(firstTeam.getName(), AssetLoader.defaultSkin)).fillY();
        infoTable.add(nestedTable);
        infoTable.add(new Label(secondTeam.getName(), AssetLoader.defaultSkin)).fillY();

        if (GameSettings.getString("control").equals("Buttons")) {
            inputController = new ButtonsInputController(infoTable);
        }
        else {
            inputController = new TouchpadInputController(infoTable);
        }

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.add(gameWorld.getGroup()).top().expand().fillX().height(Constants.FIELD_HEIGHT).row();
        mainTable.add(inputController.getTable()).bottom().fill().expand().row();
        Gdx.input.setInputProcessor(stage);

        players = new Player[2];

        Stack stack = new Stack();
        stack.setFillParent(true);

        stack.addActor(mainTable);
        stack.addActor(pauseButtonTable);
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

    public abstract void restartGame();

    public void exitGame() {
        synchronized (this) {
            notifyAll();
        }
        dispose();
        game.setScreen(previousScreen);
    }

    public int[] getScore() {
        return gameWorld.getScore();
    }

    public boolean isGameFinished() {
        return gameState == GameState.GAME_OVER;
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
