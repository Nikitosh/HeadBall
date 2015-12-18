package com.nikitosh.headball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

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
        stage.draw();
    }

}
