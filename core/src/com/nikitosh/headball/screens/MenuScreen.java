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

public class MenuScreen implements Screen {
    private Stage stage;
    private Table table;

    private final Game game;

    public MenuScreen(final Game game) {
        this.game = game;
        stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));

        table = new Table();
        table.setFillParent(true);

        Button singlePlayerTextButton = new GameTextButtonTouchable("Singleplayer");
        table.add(singlePlayerTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();

        Button multiPlayerTextButton = new GameTextButtonTouchable("Multiplayer");
        table.add(multiPlayerTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();

        Button settingsTextButton = new GameTextButtonTouchable("Settings");
        table.add(settingsTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();

        Gdx.input.setInputProcessor(stage);
        singlePlayerTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new SinglePlayerScreen(game));
            }
        });
        multiPlayerTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new MultiPlayerScreen(game));
            }
        });
        settingsTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new SettingsScreen(game));
            }
        });

        stage.addActor(table);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().draw(AssetLoader.menuTexture, 0, 0, Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT);
        stage.getBatch().end();
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
