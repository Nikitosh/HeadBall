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

public class MainMenuScreen implements Screen {
    private static final String PLAY = "Play";
    private static final String SETTINGS = "Settings";
    private static final String ABOUT = "About";

    private Stage stage;

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

        Stack stack = new Stack();
        stack.setFillParent(true);
        stack.addActor(background);
        stack.addActor(table);

        stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));
        stage.addActor(stack);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        stage.act(delta);
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
