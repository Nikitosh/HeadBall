package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.ActionResolverSingleton;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.ScreenManager;

public class MainMenuScreen extends BackgroundStageAbstractScreen {
    private static final String PLAY = "Play";
    private static final String ACHIEVEMENTS = "Achievements";
    private static final String SETTINGS = "Settings";
    private static final String CREDITS = "Credits";

    public MainMenuScreen() {
        Button playTextButton = new TextButton(PLAY, AssetLoader.getGameSkin());
        playTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setScreen(new PlayMenuScreen());
            }
        });

        Button achievementsTextButton = new TextButton(ACHIEVEMENTS, AssetLoader.getGameSkin());
        achievementsTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ActionResolverSingleton.getInstance().showAchievements();
            }
        });

        Button settingsTextButton = new TextButton(SETTINGS, AssetLoader.getGameSkin());
        settingsTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setScreen(new SettingsScreen());
            }
        });

        Button aboutTextButton = new TextButton(CREDITS, AssetLoader.getGameSkin());
        aboutTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setScreen(new AboutScreen());
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.add(playTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();
        table.add(achievementsTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();
        table.add(settingsTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();
        table.add(aboutTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();

        stack.addActor(table);
    }
}
