package com.nikitosh.headball.screens;

import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.players.AIPlayer;

public class SinglePlayerScreen extends GameScreen {

    public SinglePlayerScreen(MatchInfo matchInfo) {
        super(matchInfo);
        initializePlayers();
    }

    @Override
    protected void initializePlayers() {
        playerNumber = 0;
        players[1] = new AIPlayer(gameWorld, 1);
        super.initializePlayers();
    }

    @Override
    public void render(float delta) {
        if (gameState == GameState.GAME_RUNNING) {
            gameWorld.update(delta, players[0].getMove(), players[1].getMove());
        }
        super.render(delta);
    }
}
