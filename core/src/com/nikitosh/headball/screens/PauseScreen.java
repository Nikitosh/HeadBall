package com.nikitosh.headball.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class PauseScreen extends Window {
    public PauseScreen(final GameScreen gameScreen) {
        super("", AssetLoader.gameWindowStyle);
        setMovable(false);

        GameTextButtonTouchable continueButton = new GameTextButtonTouchable("Continue");
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.resumeGame();
            }
        });

        GameTextButtonTouchable restartButton = new GameTextButtonTouchable("Restart");
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.restartGame();
            }
        });
        GameTextButtonTouchable exitButton = new GameTextButtonTouchable("Exit");
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.exitGame();
            }
        });

        Label pauseLabel = new Label("Pause", AssetLoader.gameLabelStyle);


        add(pauseLabel).pad(Constants.BUTTON_INDENT).row();
        add(continueButton).pad(Constants.BUTTON_INDENT).row();
        add(restartButton).pad(Constants.BUTTON_INDENT).row();
        add(exitButton).pad(Constants.BUTTON_INDENT).row();
    }
}
