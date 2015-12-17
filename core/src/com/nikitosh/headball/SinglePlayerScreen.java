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
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (gameState == GameState.GAME_RUNNING) {
            gameWorld.update(delta, players[0].getMove(), players[1].getMove());
        }
        stage.draw();
    }

}
