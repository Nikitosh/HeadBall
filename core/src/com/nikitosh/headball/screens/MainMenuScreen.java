package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.ui.GameTextButtonTouchable;

public class MainMenuScreen extends StageAbstractScreen {
    private static final String PLAY = "Play";
    private static final String SETTINGS = "Settings";
    private static final String ABOUT = "About";

    public MainMenuScreen(final Game game) {
        Image background = new Image(AssetLoader.menuTexture);
        background.setFillParent(true);

        Button playTextButton = new GameTextButtonTouchable(PLAY);
        playTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PlayMenuScreen(game, MainMenuScreen.this));
            }
        });

        Button settingsTextButton = new GameTextButtonTouchable(SETTINGS);
        settingsTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game, MainMenuScreen.this));
            }
        });

        Button aboutTextButton = new GameTextButtonTouchable(ABOUT);
        aboutTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new AboutScreen(game, MainMenuScreen.this));
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(playTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();
        table.add(settingsTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();
        table.add(aboutTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();

        stack.addActor(background);
        stack.addActor(table);
    }
}
