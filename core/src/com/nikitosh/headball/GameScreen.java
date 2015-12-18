package com.nikitosh.headball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class GameScreen implements Screen {

    protected final Game game;

    protected GameWorld gameWorld;
    protected Player[] players;
    protected Stage stage;
    protected Table buttonsTable;
    protected GameTextButtonTouchable hitButton, jumpButton, leftButton, rightButton;

    protected enum GameState {GAME_RUNNING, GAME_PAUSED, GAME_OVER};
    protected GameState gameState = GameState.GAME_RUNNING;

    protected Window pauseScreen, gameOverScreen;

    protected float gameDuration = 0;

    public GameScreen(Game newGame) {
        game = newGame;

        gameWorld = new GameWorld();
        stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));

        Image background = new Image(AssetLoader.backgroundTexture);
        background.setBounds(0, 0, stage.getWidth(), stage.getHeight());
        stage.addActor(background);

        hitButton = new GameTextButtonTouchable("Hit");
        jumpButton = new GameTextButtonTouchable("Jump");
        leftButton = new GameTextButtonTouchable("Left");
        rightButton = new GameTextButtonTouchable("Right");

        buttonsTable = new Table();
        buttonsTable.setBounds(0, 0, Constants.VIRTUAL_WIDTH, Constants.BUTTONS_HEIGHT);
        buttonsTable.add(hitButton).expand().fillX().height(Constants.BUTTONS_HEIGHT);
        buttonsTable.add(jumpButton).expand().fillX().height(Constants.BUTTONS_HEIGHT);
        buttonsTable.add(leftButton).expand().fillX().height(Constants.BUTTONS_HEIGHT);
        buttonsTable.add(rightButton).expand().fillX().height(Constants.BUTTONS_HEIGHT);
        hitButton.setHeight(Constants.BUTTONS_HEIGHT);
        jumpButton.setHeight(Constants.BUTTONS_HEIGHT);
        leftButton.setHeight(Constants.BUTTONS_HEIGHT);
        rightButton.setHeight(Constants.BUTTONS_HEIGHT);

        players = new Player[2];

        GameTextButtonTouchable continueButton = new GameTextButtonTouchable("Continue");
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                resumeGame();
            }
        });

        GameTextButtonTouchable restartButton = new GameTextButtonTouchable("Restart");
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameScreen.this.dispose();
                game.setScreen(new SinglePlayerScreen(game));
            }
        });
        GameTextButtonTouchable exitButton = new GameTextButtonTouchable("Exit");
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameScreen.this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        });

        Label pauseLabel = new Label("Pause", AssetLoader.gameLabelStyle);

        pauseScreen = new Window("", AssetLoader.gameWindowStyle);
        pauseScreen.setMovable(false);
        pauseScreen.add(pauseLabel).row();
        pauseScreen.add(continueButton).row();
        pauseScreen.add(restartButton).row();
        pauseScreen.add(exitButton).row();
        pauseScreen.setBounds(Constants.VIRTUAL_WIDTH / 4, Constants.VIRTUAL_HEIGHT / 4, Constants.VIRTUAL_WIDTH / 2, Constants.VIRTUAL_HEIGHT / 2);

        GameTextButtonTouchable gameOverExitButton = new GameTextButtonTouchable("Exit");
        gameOverExitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameScreen.this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        });

        gameOverScreen = new Window("Game over", AssetLoader.gameWindowStyle);
        //gameOverScreen.padTop(20);
        gameOverScreen.setMovable(false);
        Label resultLabel = new Label("", AssetLoader.gameLabelStyle);
        gameOverScreen.add(resultLabel);
        gameOverScreen.add(gameOverExitButton);
        gameOverScreen.setBounds(Constants.VIRTUAL_WIDTH / 4, Constants.VIRTUAL_HEIGHT / 4, Constants.VIRTUAL_WIDTH / 2, Constants.VIRTUAL_HEIGHT / 2);

        GameTextButtonTouchable pauseButton = new GameTextButtonTouchable("Pause");
        pauseButton.setBounds(Constants.VIRTUAL_WIDTH - pauseButton.getWidth() - Constants.BUTTON_INDENT,
                Constants.VIRTUAL_HEIGHT - pauseButton.getHeight() - Constants.BUTTON_INDENT,
                pauseButton.getWidth(),
                pauseButton.getHeight());
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pauseGame();
            }
        });

        Gdx.input.setInputProcessor(stage);

        stage.addActor(gameWorld.getGroup());
        stage.addActor(buttonsTable);
        stage.addActor(pauseButton);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (gameState == GameState.GAME_RUNNING) {
            gameDuration += delta;
        }
        if (gameDuration > Constants.GAME_LENGTH) {
            finishGame();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        //pauseGame();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gameWorld.getBox2dWorld().dispose();
        stage.dispose();
    }

    protected abstract void initializePlayers();

    private void finishGame() {
        stage.addActor(gameOverScreen);
        gameState = GameState.GAME_OVER;
    }

    private void pauseGame() {
        stage.addActor(pauseScreen);
        gameState = GameState.GAME_PAUSED;
    }

    private void resumeGame() {
        gameState = GameState.GAME_RUNNING;
        pauseScreen.remove();
    }
}
