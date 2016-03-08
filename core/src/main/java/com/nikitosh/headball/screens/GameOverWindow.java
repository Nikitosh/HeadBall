package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.gamecontrollers.GameController;
import com.nikitosh.headball.utils.AssetLoader;

public class GameOverWindow extends Window {
    private static final String GAME_OVER = "Game over!";
    private static final String EXIT = "Exit";
    private static final String WIN_RESULT = "You win! ";
    private static final String DRAW_RESULT = "Draw! ";
    private static final String LOSS_RESULT = "You lose! ";
    private static final String SCORE = "Score: ";
    private static final String SCORE_SEPARATOR = " : ";

    private GameController gameController;
    private Label resultLabel;

    GameOverWindow(final GameController gameController) {
        super("", AssetLoader.getGameSkin());
        this.gameController = gameController;

        setMovable(false);

        Label gameOverLabel = new Label(GAME_OVER, AssetLoader.getGameSkin());
        resultLabel = new Label("", AssetLoader.getGameSkin());

        TextButton gameOverExitButton = new TextButton(EXIT, AssetLoader.getGameSkin());
        gameOverExitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameController.exitGame();
            }
        });

        add(gameOverLabel).row();
        add(resultLabel).row();
        add(gameOverExitButton).row();
    }

    private String getResultString(int[] score, int playerNumber) {
        String result = "";
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

    public void updateResult(int[] score, int playerNumber) {
        resultLabel.setText(getResultString(score, playerNumber));
    }
}
