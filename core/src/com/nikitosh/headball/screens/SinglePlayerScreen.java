package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.nikitosh.headball.players.AIPlayer;
import com.nikitosh.headball.players.LocalHumanPlayer;
import com.nikitosh.headball.utils.Constants;

public class SinglePlayerScreen extends GameScreen {

    public SinglePlayerScreen(Game game) {
        super(game);
        initializePlayers();
    }

    @Override
    protected void initializePlayers() {
        players[0] = new LocalHumanPlayer(hitButton, jumpButton, leftButton, rightButton);
        players[1] = new AIPlayer(gameWorld, 1);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (gameState == GameState.GAME_RUNNING) {
            gameWorld.update(delta, players[0].getMove(), players[1].getMove());
        }
        Gdx.app.log("Single", Float.toString(gameDuration));
        if (gameDuration > Constants.GAME_DURATION) {
            finishGame();
        }
        stage.draw();
    }

    @Override
    protected void finishGame() {
        int[] score = gameWorld.getScore();
        String scoreString = Integer.toString(score[0]) + ":" + Integer.toString(score[1]);
        if (score[0] == score[1]) {
            resultLabel.setText("Draw! " + scoreString);
        }
        else if (score[0] < score[1]) {
            resultLabel.setText("You lose! " + scoreString);
        }
        else {
            resultLabel.setText("You win! " + scoreString);
        }
        super.finishGame();
    }

}
