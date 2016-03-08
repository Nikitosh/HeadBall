package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.GameSettings;
import com.nikitosh.headball.widgets.BackButtonTable;

public class SettingsScreen extends BackgroundStageAbstractScreen {
    private static final String ENABLED = "on";
    private static final String DISABLED = "off";

    private final Button.ButtonStyle[] styles;
    private int soundState = GameSettings.getBoolean(Constants.SETTINGS_SOUND) ? 1 : 0;
    private int musicState = GameSettings.getBoolean(Constants.SETTINGS_MUSIC) ? 1 : 0;
    private final SelectBox<String> selectBox;

    public SettingsScreen() {
        styles = new Button.ButtonStyle[] {AssetLoader.getGameSkin().get(DISABLED, Button.ButtonStyle.class),
                AssetLoader.getGameSkin().get(ENABLED, Button.ButtonStyle.class)};

        Button soundTextButton = new TextButton(Constants.SETTINGS_SOUND, AssetLoader.getGameSkin(),
                "notTouchable");
        final Button soundButton = new Button(styles[soundState]);
        soundButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundState = 1 - soundState;
                soundButton.setStyle(styles[soundState]);
            }
        });

        Button musicTextButton = new TextButton(Constants.SETTINGS_MUSIC, AssetLoader.getGameSkin(),
                "notTouchable");
        final Button musicButton = new Button(styles[musicState]);
        musicButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                musicState = 1 - musicState;
                musicButton.setStyle(styles[musicState]);
            }
        });

        Button controlButton = new TextButton(Constants.SETTINGS_CONTROL, AssetLoader.getGameSkin(),
                "notTouchable");
        selectBox = new SelectBox<>(AssetLoader.getGameSkin());
        selectBox.setItems(Constants.SETTINGS_CONTROL_BUTTONS, Constants.SETTINGS_CONTROL_TOUCHPAD,
                Constants.SETTINGS_CONTROL_KEYBOARD);
        selectBox.setSelected(GameSettings.getString(Constants.SETTINGS_CONTROL));

        Table settingsTable = new Table();
        settingsTable.defaults().pad(Constants.UI_ELEMENTS_INDENT);
        settingsTable.setFillParent(true);
        settingsTable.add(soundTextButton);
        settingsTable.add(soundButton).row();
        settingsTable.add(musicTextButton);
        settingsTable.add(musicButton).row();
        settingsTable.add(controlButton);
        settingsTable.add(selectBox);

        stack.addActor(new BackButtonTable(new Runnable() {
            //runnable is used to save settings when "back" button pressed
            @Override
            public void run() {
                GameSettings.putBoolean(Constants.SETTINGS_SOUND, soundState != 0);
                GameSettings.putBoolean(Constants.SETTINGS_MUSIC, musicState != 0);
                GameSettings.putString(Constants.SETTINGS_CONTROL, selectBox.getSelected());
            }
        }));
        stack.addActor(settingsTable);
    }
}
