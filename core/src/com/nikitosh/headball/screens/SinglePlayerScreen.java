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
        playerNumber = 0;
        //players[0] = new LocalHumanPlayer(hitButton, jumpButton, leftButton, rightButton);
        players[1] = new AIPlayer(gameWorld, 1);
        super.initializePlayers();
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
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void restartGame() {
        dispose();
        game.setScreen(new SinglePlayerScreen(game));
    }
}
