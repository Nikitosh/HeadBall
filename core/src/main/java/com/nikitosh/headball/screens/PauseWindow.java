package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.gamecontrollers.GameController;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class PauseWindow extends Window {
    private static final String PAUSE = "Pause";
    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";

    private TextButton restartButton;
    private TextButton exitButton;

    public PauseWindow(final GameController gameController) {
        super("", AssetLoader.getGameSkin());
        setMovable(false);
        defaults().pad(Constants.UI_ELEMENTS_INDENT);

        Label pauseLabel = new Label(PAUSE, AssetLoader.getGameSkin());

        TextButton continueButton = new TextButton(CONTINUE, AssetLoader.getGameSkin());
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameController.resumeGame();
            }
        });

        add(pauseLabel).pad(Constants.UI_ELEMENTS_INDENT).row();
        add(continueButton).pad(Constants.UI_ELEMENTS_INDENT).row();

        restartButton = new TextButton(RESTART, AssetLoader.getGameSkin());
        restartButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    gameController.restartGame();
                }
            });

        exitButton = new TextButton(EXIT, AssetLoader.getGameSkin());
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameController.exitGame();
            }
        });

        add(restartButton).row();
        add(exitButton).row();
    }

    public void setRestartOrExitAvailable(boolean restartOrExitAvailable) {
        restartButton.setVisible(restartOrExitAvailable);
        exitButton.setVisible(restartOrExitAvailable);
    }
}
