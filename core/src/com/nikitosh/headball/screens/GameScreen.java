package com.nikitosh.headball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.nikitosh.headball.jsonReaders.LevelReader;
import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.ScreenManager;
import com.nikitosh.headball.controllers.ButtonsInputController;
import com.nikitosh.headball.controllers.InputController;
import com.nikitosh.headball.controllers.KeyboardInputController;
import com.nikitosh.headball.controllers.TouchpadInputController;
import com.nikitosh.headball.players.LocalHumanPlayer;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.GameWorld;
import com.nikitosh.headball.players.Player;
import com.nikitosh.headball.utils.GameSettings;

public abstract class GameScreen extends StageAbstractScreen {
    private static final String PAUSE = "Pause";
    private static final String SCORE_SEPARATOR = " : ";
    private static final int MILLISECONDS = 1000;

    private static final String LOG_TAG = "GameScreen";

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

    private long startTime = System.currentTimeMillis();

    public GameScreen(MatchInfo matchInfo) {
        gameWorld = LevelReader.loadLevel(matchInfo.getLevelNumber());
        gameWorld.setDrawResultPossible(matchInfo.isDrawResultPossible());

        Image background = new Image(AssetLoader.getBackgroundTexture());
        background.setFillParent(true);

        darkBackground = new Image(AssetLoader.getDarkBackgroundTexture());
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

        TextButton pauseButton = new TextButton(PAUSE, AssetLoader.getGameSkin());
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseGame();
            }
        });

        pauseButtonTable = new Table();
        pauseButtonTable.setFillParent(true);
        pauseButtonTable.add(pauseButton).top().right().expand().pad(Constants.UI_ELEMENTS_INDENT).row();

        timerLabel = new Label("", AssetLoader.getDefaultSkin());
        scoreLabel = new Label("", AssetLoader.getDefaultSkin());
        timerLabel.setAlignment(Align.center);
        scoreLabel.setAlignment(Align.center);

        Table infoTable = new Table();
        Table nestedTable = new Table();
        nestedTable.add(timerLabel).fillX().row();
        nestedTable.add(scoreLabel).fillX();
        infoTable.add(new Label(matchInfo.getFirstTeam().getName(), AssetLoader.getDefaultSkin())).fillY();
        infoTable.add(nestedTable);
        infoTable.add(new Label(matchInfo.getSecondTeam().getName(), AssetLoader.getDefaultSkin())).fillY();

        if (GameSettings.getString(Constants.SETTINGS_CONTROL).equals(Constants.SETTINGS_CONTROL_BUTTONS)) {
            inputController = new ButtonsInputController(infoTable);
        } else if (GameSettings.getString(Constants.SETTINGS_CONTROL).
                equals(Constants.SETTINGS_CONTROL_TOUCHPAD)) {
            inputController = new TouchpadInputController(infoTable);
        } else if (GameSettings.getString(Constants.SETTINGS_CONTROL).
                equals(Constants.SETTINGS_CONTROL_KEYBOARD)) {
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
        if (gameState == GameState.GAME_RUNNING && gameWorld.isGoal()
                && GameSettings.getBoolean(Constants.SETTINGS_SOUND)) {
            AssetLoader.getGoalSound().play();
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
        ScreenManager.getInstance().disposeCurrentScreen();
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
        timerLabel.setText(String.format("%02d",
                Math.max(0, Constants.GAME_DURATION - (int) gameWorld.getGameDuration())));
    }

    public float getDelta(int fps) {
        long diff = System.currentTimeMillis() - startTime;
        long targetDelay = MILLISECONDS / fps;
        if (diff < targetDelay) {
            try {
                Thread.sleep(targetDelay - diff);
            } catch (InterruptedException e) {
                Gdx.app.error(LOG_TAG, "", e);
            }
        }
        long currentTime = System.currentTimeMillis();
        float delta = (currentTime - startTime) / (float) MILLISECONDS;
        startTime = currentTime;
        return delta;
    }
}
