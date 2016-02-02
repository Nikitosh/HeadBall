package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.nikitosh.headball.GameWorld;
import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.players.AIPlayer;

public class SinglePlayerScreen extends GameScreen {

    public SinglePlayerScreen(Game game, Screen previousScreen, MatchInfo matchInfo) {
        super(game, previousScreen, matchInfo);
        initializePlayers();
    }

    @Override
    protected void initializePlayers() {
        playerNumber = 0;
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
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void restartGame() {
        super.restartGame();
        players[1] = new AIPlayer(gameWorld, 1);
    }
}
