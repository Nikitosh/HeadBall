package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class PauseScreen extends Window {
    private static final String PAUSE = "Pause";
    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";

    public PauseScreen(final GameScreen gameScreen) {
        super("", AssetLoader.gameWindowStyle);
        setMovable(false);

        Label pauseLabel = new Label(PAUSE, AssetLoader.gameLabelStyle);

        GameTextButtonTouchable continueButton = new GameTextButtonTouchable(CONTINUE);
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.resumeGame();
            }
        });

        GameTextButtonTouchable restartButton = new GameTextButtonTouchable(RESTART);
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.restartGame();
            }
        });

        GameTextButtonTouchable exitButton = new GameTextButtonTouchable(EXIT);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.exitGame();
            }
        });

        add(pauseLabel).pad(Constants.UI_ELEMENTS_INDENT).row();
        add(continueButton).pad(Constants.UI_ELEMENTS_INDENT).row();
        add(restartButton).pad(Constants.UI_ELEMENTS_INDENT).row();
        add(exitButton).pad(Constants.UI_ELEMENTS_INDENT).row();
    }
}
