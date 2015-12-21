package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.utils.AssetLoader;

public class GameOverScreen extends Window {
    private GameScreen gameScreen;
    private Label resultLabel;

    GameOverScreen(final GameScreen gameScreen) {
        super("", AssetLoader.gameWindowStyle);
        this.gameScreen = gameScreen;

        setMovable(false);

        Label gameOverLabel = new Label("Game over!", AssetLoader.gameLabelStyle);
        resultLabel = new Label("", AssetLoader.gameLabelStyle);

        GameTextButtonTouchable gameOverExitButton = new GameTextButtonTouchable("Exit");
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
            result += "Draw! ";
        }
        if (score[playerNumber] > score[1 - playerNumber]) {
            result += "You win! ";
        }
        if (score[playerNumber] < score[1 - playerNumber]) {
            result += "You lose! ";
        }
        result += "Score: " + Integer.toString(score[0]) + " : " + Integer.toString(score[1]);
        return result;
    }

    public void updateResult() {
        resultLabel.setText(getResultString());
    }
}
