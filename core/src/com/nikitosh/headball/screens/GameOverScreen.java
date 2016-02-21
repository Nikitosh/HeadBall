package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.utils.AssetLoader;

public class GameOverScreen extends Window {
    private static final String GAME_OVER = "Game over!";
    private static final String EXIT = "Exit";
    private static final String WIN_RESULT = "You win! ";
    private static final String DRAW_RESULT = "Draw! ";
    private static final String LOSS_RESULT = "You lose! ";
    private static final String SCORE = "Score: ";
    private static final String SCORE_SEPARATOR = " : ";

    private GameScreen gameScreen;
    private Label resultLabel;

    GameOverScreen(final GameScreen gameScreen) {
        super("", AssetLoader.gameSkin);
        this.gameScreen = gameScreen;

        setMovable(false);

        Label gameOverLabel = new Label(GAME_OVER, AssetLoader.gameSkin);
        resultLabel = new Label("", AssetLoader.gameSkin);

        TextButton gameOverExitButton = new TextButton(EXIT, AssetLoader.gameSkin);
        gameOverExitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.exitGame();
            }
        });

        add(gameOverLabel).row();
        add(resultLabel).row();
        add(gameOverExitButton).row();
    }

    private String getResultString() {
        int[] score = gameScreen.getScore();
        String result = "";
        int playerNumber = gameScreen.getPlayerNumber();
        if (score[playerNumber] == score[1 - playerNumber]) {
            result += DRAW_RESULT;
        }
        if (score[playerNumber] > score[1 - playerNumber]) {
            result += WIN_RESULT;
        }
        if (score[playerNumber] < score[1 - playerNumber]) {
            result += LOSS_RESULT;
        }
        result += SCORE + Integer.toString(score[0]) + SCORE_SEPARATOR + Integer.toString(score[1]);
        return result;
    }

    public void updateResult() {
        resultLabel.setText(getResultString());
    }
}
