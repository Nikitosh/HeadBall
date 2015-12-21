package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.utils.AssetLoader;

public class GameOverScreen extends Window {
    GameOverScreen(final GameScreen gameScreen) {
        super("", AssetLoader.gameWindowStyle);
        setMovable(false);

        Label gameOverLabel = new Label("Game over!", AssetLoader.gameLabelStyle);
        Label resultLabel = new Label("", AssetLoader.gameLabelStyle);

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
}
