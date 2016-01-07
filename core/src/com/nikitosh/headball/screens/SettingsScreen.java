package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.GameSettings;
import com.nikitosh.headball.ui.GameButtonStyle;
import com.nikitosh.headball.ui.GameTextButton;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.widgets.BackButtonTable;

public class SettingsScreen implements Screen {
    private static final String ENABLED_ICON_NAME = "red_boxCheckmark";
    private static final String DISABLED_ICON_NAME = "red_boxCross";

    private Stage stage;
    private Table settingsTable;

    private Drawable[] drawables;
    private int soundState = GameSettings.getBoolean(Constants.SETTINGS_SOUND) ? 1 : 0;
    private int musicState = GameSettings.getBoolean(Constants.SETTINGS_MUSIC) ? 1 : 0;
    private SelectBox<String> selectBox;

    public SettingsScreen(final Game game, final Screen previousScreen) {
        Image background = new Image(AssetLoader.menuTexture);
        background.setFillParent(true);

        drawables = new Drawable[] {AssetLoader.skin.getDrawable(DISABLED_ICON_NAME),
                AssetLoader.skin.getDrawable(ENABLED_ICON_NAME)};

        Button soundTextButton = new GameTextButton(Constants.SETTINGS_SOUND);
        final Button soundButton = new Button(new GameButtonStyle(ENABLED_ICON_NAME));
        soundButton.getStyle().up = drawables[soundState];
        soundButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundState = 1 - soundState;
                soundButton.getStyle().up = drawables[soundState];
            }
        });

        Button musicTextButton = new GameTextButton(Constants.SETTINGS_MUSIC);
        final Button musicButton = new Button(new GameButtonStyle(ENABLED_ICON_NAME));
        musicButton.getStyle().up = drawables[musicState];
        musicButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                musicState = 1 - musicState;
                musicButton.getStyle().up = drawables[musicState];
            }
        });

        Button controlButton = new GameTextButton(Constants.SETTINGS_CONTROL);
        selectBox = new SelectBox<String>(AssetLoader.defaultSkin);
        selectBox.setItems(new String[]{Constants.SETTINGS_CONTROL_BUTTONS, Constants.SETTINGS_CONTROL_TOUCHPAD,
                Constants.SETTINGS_CONTROL_KEYBOARD});
        selectBox.setSelected(GameSettings.getString(Constants.SETTINGS_CONTROL));

        settingsTable = new Table();
        settingsTable.setFillParent(true);
        settingsTable.add(soundTextButton).pad(Constants.UI_ELEMENTS_INDENT);
        settingsTable.add(soundButton).pad(Constants.UI_ELEMENTS_INDENT).row();
        settingsTable.add(musicTextButton).pad(Constants.UI_ELEMENTS_INDENT);
        settingsTable.add(musicButton).pad(Constants.UI_ELEMENTS_INDENT).row();
        settingsTable.add(controlButton).pad(Constants.UI_ELEMENTS_INDENT);
        settingsTable.add(selectBox).pad(Constants.UI_ELEMENTS_INDENT);

        Stack stack = new Stack();
        stack.setFillParent(true);
        stack.addActor(background);
        stack.addActor(new BackButtonTable(game, this, previousScreen, new Runnable() {
            @Override
            public void run() {
                GameSettings.putBoolean(Constants.SETTINGS_SOUND, soundState != 0);
                GameSettings.putBoolean(Constants.SETTINGS_MUSIC, musicState != 0);
                GameSettings.putString(Constants.SETTINGS_CONTROL, selectBox.getSelected());
            }
        }));
        stack.addActor(settingsTable);

        stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));
        stage.addActor(stack);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
