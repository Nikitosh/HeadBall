package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.ScreenManager;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class MainMenuScreen extends BackgroundStageAbstractScreen {
    private static final String PLAY = "Play";
    private static final String SETTINGS = "Settings";
    private static final String ABOUT = "About";

    public MainMenuScreen() {
        Button playTextButton = new TextButton(PLAY, AssetLoader.gameSkin);
        playTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setScreen(new PlayMenuScreen());
            }
        });

        Button settingsTextButton = new TextButton(SETTINGS, AssetLoader.gameSkin);
        settingsTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setScreen(new SettingsScreen());
            }
        });

        Button aboutTextButton = new TextButton(ABOUT, AssetLoader.gameSkin);
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

        stack.addActor(table);
    }
}
