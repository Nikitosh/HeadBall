package com.nikitosh.headball.gamecontrollers;

import com.badlogic.gdx.Gdx;
import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.inputcontrollers.*;
import com.nikitosh.headball.utils.ScreenManager;
import com.nikitosh.headball.players.Player;
import com.nikitosh.headball.screens.GameScreen;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.GameSettings;

public abstract class GameController {
    private static final int MILLISECONDS = 1000;
    private static final String LOG_TAG = "GameController";

    protected final GameScreen gameScreen;

    private final MatchInfo matchInfo;
    private InputController inputController;

    protected final Player[] players;
    protected int playerNumber;
    private long startTime = System.currentTimeMillis();
    protected float accumulator = 0;

    protected enum GameState {GAME_RUNNING, GAME_PAUSED, GAME_OVER}
    protected GameState gameState = GameState.GAME_RUNNING;

    public GameController(GameScreen gameScreen, MatchInfo matchInfo) {
        this.gameScreen = gameScreen;
        this.matchInfo = matchInfo;

        String control = GameSettings.getString(Constants.SETTINGS_CONTROL);
        if (control.equals(Constants.SETTINGS_CONTROL_BUTTONS)) {
            inputController = new ButtonsInputController();
        } else if (control.equals(Constants.SETTINGS_CONTROL_TOUCHPAD)) {
            inputController = new TouchpadInputController();
        } else if (control.equals(Constants.SETTINGS_CONTROL_KEYBOARD)) {
            inputController = new KeyboardInputController();
        } else if (control.equals(Constants.SETTINGS_CONTROL_ACCELEROMETER)) {
            inputController = new AccelerometerInputController();
        }

        players = new Player[Constants.PLAYERS_NUMBER];

        gameScreen.addUILayer(inputController,
                matchInfo.getFirstTeam().getName(), matchInfo.getSecondTeam().getName());
        gameScreen.addPauseButton();

        gameScreen.initializeController(this);
        gameScreen.initializeWindows();
    }

    public float getDelta() {
        long diff = System.currentTimeMillis() - startTime;
        long targetDelay = MILLISECONDS / Constants.FRAMES_PER_SECOND;
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

    public abstract void update();

    protected void finishGame() {
        gameState = GameState.GAME_OVER;
    }

    public void pauseGame() {
        accumulator += getDelta();
        gameScreen.addPauseWindow(matchInfo.isRestartOrExitAvailable());
        gameState = GameState.GAME_PAUSED;
    }

    public void resumeGame() {
        gameScreen.removePauseWindow();
        gameState = GameState.GAME_RUNNING;
        startTime = System.currentTimeMillis();
    }

    public void restartGame() {
        gameScreen.removePauseWindow();
        gameState = GameState.GAME_RUNNING;
        startTime = System.currentTimeMillis();
        accumulator = 0;
    }

    public void exitGame() {
        synchronized (this) {
            notifyAll();
        }
        ScreenManager.getInstance().disposeCurrentScreen();
    }

    public boolean isGameNotFinished() {
        return gameState != GameState.GAME_OVER;
    }

    public abstract int[] getScore();

    public InputController getInputController() {
        return inputController;
    }
}
