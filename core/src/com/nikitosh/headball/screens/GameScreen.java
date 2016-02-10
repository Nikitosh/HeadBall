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
import com.nikitosh.headball.LevelLoader;
import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.controllers.ButtonsInputController;
import com.nikitosh.headball.controllers.InputController;
import com.nikitosh.headball.controllers.KeyboardInputController;
import com.nikitosh.headball.controllers.TouchpadInputController;
import com.nikitosh.headball.players.LocalHumanPlayer;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.GameWorld;
import com.nikitosh.headball.players.Player;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.utils.GameSettings;

public abstract class GameScreen extends StageAbstractScreen {
    private static final String PAUSE = "Pause";
    private static final String SCORE_SEPARATOR = " : ";

    protected final Game game;
    protected Screen previousScreen;

    protected GameWorld gameWorld;
    protected Player[] players;

    protected Table mainTable = new Table();
    protected Table pauseButtonTable;
    protected InputController inputController;

    private Label scoreLabel;
    private Label timerLabel;

    protected enum GameState {GAME_RUNNING, GAME_PAUSED, GAME_OVER}
    protected GameState gameState = GameState.GAME_RUNNING;

    protected Window pauseScreen;
    protected GameOverScreen gameOverScreen;
    protected Image darkBackground;

    protected int playerNumber;

    public GameScreen(Game newGame, Screen previousScreen, MatchInfo matchInfo) {
        game = newGame;
        this.previousScreen = previousScreen;

        gameWorld = LevelLoader.loadLevel(matchInfo.getLevelNumber());
        gameWorld.setDrawResultPossible(matchInfo.isDrawResultPossible());

        Image background = new Image(AssetLoader.backgroundTexture);
        background.setFillParent(true);

        darkBackground = new Image(AssetLoader.darkBackgroundTexture);
        darkBackground.setFillParent(true);

        pauseScreen = new PauseScreen(this, matchInfo.isRestartOrExitPossible());
        pauseScreen.setBounds(Constants.VIRTUAL_WIDTH / 4,
                Constants.VIRTUAL_HEIGHT / 4,
                Constants.VIRTUAL_WIDTH / 2,
                Constants.VIRTUAL_HEIGHT / 2);

        gameOverScreen = new GameOverScreen(this);
        gameOverScreen.setBounds(Constants.VIRTUAL_WIDTH / 4,
                Constants.VIRTUAL_HEIGHT / 4,
                Constants.VIRTUAL_WIDTH / 2,
                Constants.VIRTUAL_HEIGHT / 2);

        GameTextButtonTouchable pauseButton = new GameTextButtonTouchable(PAUSE);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseGame();
            }
        });

        pauseButtonTable = new Table();
        pauseButtonTable.setFillParent(true);
        pauseButtonTable.add(pauseButton).top().right().expand().pad(Constants.UI_ELEMENTS_INDENT).row();

        timerLabel = new Label("", AssetLoader.defaultSkin);
        scoreLabel = new Label("", AssetLoader.defaultSkin);
        timerLabel.setAlignment(Align.center);
        scoreLabel.setAlignment(Align.center);

        Table infoTable = new Table();
        Table nestedTable = new Table();
        nestedTable.add(timerLabel).fillX().row();
        nestedTable.add(scoreLabel).fillX();
        infoTable.add(new Label(matchInfo.getFirstTeam().getName(), AssetLoader.defaultSkin)).fillY();
        infoTable.add(nestedTable);
        infoTable.add(new Label(matchInfo.getSecondTeam().getName(), AssetLoader.defaultSkin)).fillY();

        if (GameSettings.getString(Constants.SETTINGS_CONTROL).equals(Constants.SETTINGS_CONTROL_BUTTONS)) {
            inputController = new ButtonsInputController(infoTable);
        }
        else if (GameSettings.getString(Constants.SETTINGS_CONTROL).equals(Constants.SETTINGS_CONTROL_TOUCHPAD)) {
            inputController = new TouchpadInputController(infoTable);
        }
        else if (GameSettings.getString(Constants.SETTINGS_CONTROL).equals(Constants.SETTINGS_CONTROL_KEYBOARD)) {
            inputController = new KeyboardInputController(infoTable);
        }

        mainTable.setFillParent(true);
        initializeMainTable();

        players = new Player[Constants.PLAYERS_NUMBER];

        stack.addActor(background);
        stack.addActor(mainTable);
        stack.addActor(pauseButtonTable);
    }

    @Override
    public void render(float delta) {
        if (gameState == GameState.GAME_RUNNING && gameWorld.isGoal() && GameSettings.getBoolean(Constants.SETTINGS_SOUND)) {
            AssetLoader.goalSound.play();
        }
        if (gameWorld.isEnded()) {
            finishGame();
        }
        updateHUD();
        super.render(delta);
    }

    @Override
    public void pause() {
        pauseGame();
    }

    @Override
    public void dispose() {
        super.dispose();
        gameWorld.getBox2dWorld().dispose();
    }

    protected void initializePlayers() {
        players[playerNumber] = new LocalHumanPlayer(inputController);
    }


    public void finishGame() {
        stage.addActor(darkBackground);
        stage.addActor(gameOverScreen);
        gameOverScreen.updateResult();
        gameState = GameState.GAME_OVER;

    }

    public void pauseGame() {
        stage.addActor(darkBackground);
        stage.addActor(pauseScreen);
        gameState = GameState.GAME_PAUSED;
    }

    public void resumeGame() {
        gameState = GameState.GAME_RUNNING;
        pauseScreen.remove();
        darkBackground.remove();
    }

    public void restartGame() {
        pauseScreen.remove();
        darkBackground.remove();
        gameWorld.restartGame();
        gameState = GameState.GAME_RUNNING;
    }

    private void initializeMainTable() {
        mainTable.add(gameWorld.getGroup()).top().expand().fillX().height(gameWorld.getHeight()).row();
        mainTable.add(inputController.getTable()).bottom().fill().expand().row();
    }

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
        scoreLabel.setText(Integer.toString(score[0]) + SCORE_SEPARATOR + Integer.toString(score[1]));
        timerLabel.setText(String.format("%02d", Math.max(0, Constants.GAME_DURATION - (int) gameWorld.getGameDuration())));
    }
}
