package com.nikitosh.headball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class GameScreen implements Screen {

    protected final Game game;

    protected GameWorld gameWorld;
    protected Player[] players;
    protected Stage stage;
    protected Table buttonsTable;
    protected GameTextButton hitButton, jumpButton, leftButton, rightButton;

    protected enum GameState {GAME_RUNNING, GAME_PAUSED, GAME_OVER};
    protected GameState gameState = GameState.GAME_RUNNING;

    protected Window pauseScreen, gameOverScreen;

    protected long gameStartTime = 0;

    public GameScreen(Game newGame) {
        game = newGame;

        gameWorld = new GameWorld();
        stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));

        hitButton = new GameTextButton("Hit");
        jumpButton = new GameTextButton("Jump");
        leftButton = new GameTextButton("Left");
        rightButton = new GameTextButton("Right");

        buttonsTable = new Table();
        buttonsTable.setBounds(0, 0, Constants.VIRTUAL_WIDTH, Constants.BUTTONS_HEIGHT);
        buttonsTable.add(hitButton).expand().fillX();
        buttonsTable.add(jumpButton).expand().fillX();
        buttonsTable.add(leftButton).expand().fillX();
        buttonsTable.add(rightButton).expand().fillX();

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

        pauseScreen = new Window("Pause", AssetLoader.gameWindowStyle);
        pauseScreen.padTop(20);
        pauseScreen.setMovable(false);
        pauseScreen.add(continueButton).row();
        pauseScreen.add(restartButton).row();
        pauseScreen.add(exitButton).row();
        pauseScreen.setBounds(Constants.VIRTUAL_WIDTH / 4, Constants.VIRTUAL_HEIGHT / 4, Constants.VIRTUAL_WIDTH / 2, Constants.VIRTUAL_HEIGHT / 2);

        GameTextButtonTouchable gameOverExitButton = new GameTextButtonTouchable("Exit");
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
            }
        });
        gameOverScreen = new Window("Game over", AssetLoader.gameWindowStyle);
        //gameOverScreen.padTop(20);
        gameOverScreen.setMovable(false);
        Label resultLabel = new Label("", AssetLoader.gameLabelStyle);
        gameOverScreen.add(resultLabel);
        gameOverScreen.add(gameOverExitButton);
        gameOverScreen.setBounds(Constants.VIRTUAL_WIDTH / 4, Constants.VIRTUAL_HEIGHT / 3, 3 * Constants.VIRTUAL_WIDTH / 4, 2 * Constants.VIRTUAL_HEIGHT / 3);

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
        gameStartTime = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        pauseGame();
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
