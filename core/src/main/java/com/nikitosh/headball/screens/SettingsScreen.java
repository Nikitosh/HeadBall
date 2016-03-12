package com.nikitosh.headball.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.GameSettings;
import com.nikitosh.headball.widgets.BackButtonTable;

public class SettingsScreen extends BackgroundStageAbstractScreen {
    private static final String ENABLED = "on";
    private static final String DISABLED = "off";
    private static final int DURATION_LABEL_WIDTH = 100;

    private final Button.ButtonStyle[] styles;
    private int soundState = GameSettings.getBoolean(Constants.SETTINGS_SOUND) ? 1 : 0;
    private int musicState = GameSettings.getBoolean(Constants.SETTINGS_MUSIC) ? 1 : 0;

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
        final SelectBox<String> controlSelectBox = new SelectBox<>(AssetLoader.getGameSkin());
        Array<String> controls = new Array<>(new String[] {
                Constants.SETTINGS_CONTROL_BUTTONS, Constants.SETTINGS_CONTROL_TOUCHPAD});
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            controls.add(Constants.SETTINGS_CONTROL_ACCELEROMETER);
        }
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            controls.add(Constants.SETTINGS_CONTROL_KEYBOARD);
        }
        controlSelectBox.setItems(controls);
        controlSelectBox.setSelected(GameSettings.getString(Constants.SETTINGS_CONTROL));


        Button durationButton = new TextButton(Constants.GAME_DURATION, AssetLoader.getGameSkin(),
                "notTouchable");
        final Label durationLabel = new Label(Integer.toString(GameSettings.getInteger(Constants.GAME_DURATION)),
                AssetLoader.getGameSkin(), "background");
        durationLabel.setAlignment(Align.center);
        final Slider slider = new Slider(
                Constants.GAME_DURATION_OPTIONS[0],
                Constants.GAME_DURATION_OPTIONS[Constants.GAME_DURATION_OPTIONS.length - 1],
                Constants.GAME_DURATION_OPTIONS[0],
                false, AssetLoader.getGameSkin()); //false means horizontal slider
        slider.setValue(GameSettings.getInteger(Constants.GAME_DURATION));
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                durationLabel.setText(Integer.toString((int) slider.getValue()));
            }
        });

        Table durationTable = new Table();
        durationTable.add(slider).pad(Constants.UI_ELEMENTS_INDENT);
        durationTable.add(durationLabel).width(DURATION_LABEL_WIDTH);

        Button botButton = new TextButton(Constants.AI_LEVEL, AssetLoader.getGameSkin(), "notTouchable");
        final SelectBox<String> botSelectBox = new SelectBox<>(AssetLoader.getGameSkin());
        botSelectBox.setItems(new Array<>(new String[] {
                Constants.AI_LEVEL_EASY, Constants.AI_LEVEL_MEDIUM, Constants.AI_LEVEL_HARD}));
        botSelectBox.setSelected(GameSettings.getString(Constants.AI_LEVEL));

        Table settingsTable = new Table();
        settingsTable.defaults().pad(Constants.UI_ELEMENTS_INDENT);
        settingsTable.setFillParent(true);
        settingsTable.add(soundTextButton);
        settingsTable.add(soundButton).row();
        settingsTable.add(musicTextButton);
        settingsTable.add(musicButton).row();
        settingsTable.add(controlButton);
        settingsTable.add(controlSelectBox).row();
        settingsTable.add(durationButton);
        settingsTable.add(durationTable).row();
        settingsTable.add(botButton);
        settingsTable.add(botSelectBox).row();

        stack.addActor(new BackButtonTable(new Runnable() {
            //runnable is used to save settings when "back" button pressed
            @Override
            public void run() {
                GameSettings.putBoolean(Constants.SETTINGS_SOUND, soundState != 0);
                GameSettings.putBoolean(Constants.SETTINGS_MUSIC, musicState != 0);
                GameSettings.putString(Constants.SETTINGS_CONTROL, controlSelectBox.getSelected());
                GameSettings.putInteger(Constants.GAME_DURATION, (int) slider.getValue());
                GameSettings.putString(Constants.AI_LEVEL, botSelectBox.getSelected());
            }
        }));
        stack.addActor(settingsTable);
    }
}
