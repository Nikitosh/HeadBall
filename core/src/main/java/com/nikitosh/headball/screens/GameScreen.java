package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.gamecontrollers.GameController;
import com.nikitosh.headball.inputcontrollers.InputController;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.widgets.GameFieldGroup;

public class GameScreen extends StageAbstractScreen {
    private static final String PAUSE = "Pause";
    private static final String SCORE_SEPARATOR = " : ";

    private final Table mainTable = new Table();
    private final GameFieldGroup gameField = new GameFieldGroup();

    private Label scoreLabel;
    private Label timerLabel;

    private PauseWindow pauseWindow;
    private GameOverWindow gameOverWindow;

    private GameController gameController;

    public GameScreen() {
        Image background = new Image(AssetLoader.getBackgroundTexture());
        background.setFillParent(true);

        mainTable.setFillParent(true);
        mainTable.add(gameField).row();

        stack.addActor(background);
        stack.addActor(mainTable);
    }

    public void initializeController(GameController gameController) {
        this.gameController = gameController;
    }
    public void initializeWindows() {
        pauseWindow = new PauseWindow(gameController);
        pauseWindow.setBounds(Constants.VIRTUAL_WIDTH / 4,
                Constants.VIRTUAL_HEIGHT / 4,
                Constants.VIRTUAL_WIDTH / 2,
                Constants.VIRTUAL_HEIGHT / 2);

        gameOverWindow = new GameOverWindow(gameController);
        gameOverWindow.setBounds(Constants.VIRTUAL_WIDTH / 4,
                Constants.VIRTUAL_HEIGHT / 4,
                Constants.VIRTUAL_WIDTH / 2,
                Constants.VIRTUAL_HEIGHT / 2);
    }

    @Override
    public void pause() {
        gameController.pauseGame();
    }

    @Override
    public void render(float delta) {
        gameController.update();
        super.render(delta);
    }

    public void addGameOverWindow(int[] score, int playerNumber) {
        stage.addActor(AssetLoader.getDarkBackgroundImage());
        stage.addActor(gameOverWindow);
        gameOverWindow.updateResult(score, playerNumber);
    }

    public void addPauseWindow(boolean isRestartOrExitPossible) {
        stage.addActor(AssetLoader.getDarkBackgroundImage());
        stage.addActor(pauseWindow);
        pauseWindow.setRestartOrExitAvailable(isRestartOrExitPossible);
    }

    public void removePauseWindow() {
        pauseWindow.remove();
        AssetLoader.getDarkBackgroundImage().remove();
    }

    public void addUILayer(InputController controller, String teamName1, String teamName2) {
        timerLabel = new Label("", AssetLoader.getDefaultSkin());
        scoreLabel = new Label("", AssetLoader.getDefaultSkin());
        timerLabel.setAlignment(Align.center);
        scoreLabel.setAlignment(Align.center);

        Table scoreTable = new Table();
        Table nestedTable = new Table();
        nestedTable.add(timerLabel).fillX().row();
        nestedTable.add(scoreLabel).fillX();
        scoreTable.add(new Label(teamName1, AssetLoader.getDefaultSkin())).fillY();
        scoreTable.add(nestedTable);
        scoreTable.add(new Label(teamName2, AssetLoader.getDefaultSkin())).fillY();

        Table uiTable = new Table();
        uiTable.add(controller.getLeftTable()).left();
        uiTable.add(scoreTable).expand();
        uiTable.add(controller.getRightTable()).right();
        mainTable.add(uiTable).bottom().expand().fillX();
    }

    public void addPauseButton() {
        TextButton pauseButton = new TextButton(PAUSE, AssetLoader.getGameSkin());
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameController.pauseGame();
            }
        });

        Table pauseButtonTable = new Table();
        pauseButtonTable.setFillParent(true);
        pauseButtonTable.add(pauseButton).top().right().expand().pad(Constants.UI_ELEMENTS_INDENT).row();
        stack.addActor(pauseButtonTable);
    }

    public void updateScoreLabel(int[] score) {
        scoreLabel.setText(Integer.toString(score[0]) + SCORE_SEPARATOR + Integer.toString(score[1]));
    }

    public void updateTimerLabel(int remainingTime) {
        timerLabel.setText(String.format("%02d", Math.max(0, remainingTime)));
    }

    public void drawBall(float x, float y, float radius, float angle) {
        gameField.setSpritePosition(Constants.BALL, x, y, 2 * radius, 2 * radius, angle);
    }

    public void drawFootballer(int i, float x, float y, float radius, float angle) {
        gameField.setSpritePosition(Constants.FOOTBALLER + i, x, y, 2 * radius, 2 * radius, angle);
    }

    public void drawLeg(int i, float x, float y, float width, float height,  float angle) {
        gameField.setSpritePosition(Constants.LEG + i, x, y, width, height, angle);
    }

    public void drawGoals(int i, float x, float y, float width, float height, float angle) {
        gameField.setSpritePosition(Constants.GOALS + i, x, y, width, height, angle);
    }

    public void drawWall(Array<Array<Float>> walls) {
        gameField.setWallsPosition(walls);
    }

    public Group getField() {
        return gameField;
    }
}
