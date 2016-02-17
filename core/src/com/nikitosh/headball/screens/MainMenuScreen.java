package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.ScreenManager;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.ui.GameTextButtonTouchable;

public class MainMenuScreen extends StageAbstractScreen {
    private static final String PLAY = "Play";
    private static final String SETTINGS = "Settings";
    private static final String ABOUT = "About";

    public MainMenuScreen() {
        Image background = new Image(AssetLoader.menuTexture);
        background.setFillParent(true);

        Button playTextButton = new GameTextButtonTouchable(PLAY);
        playTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setScreen(new PlayMenuScreen());
            }
        });

        Button settingsTextButton = new GameTextButtonTouchable(SETTINGS);
        settingsTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setScreen(new SettingsScreen());
            }
        });

        Button aboutTextButton = new GameTextButtonTouchable(ABOUT);
        aboutTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setScreen(new AboutScreen());
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
