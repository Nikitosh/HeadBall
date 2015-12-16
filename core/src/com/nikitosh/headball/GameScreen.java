package com.nikitosh.headball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {

    private GameWorld gameWorld;
    private Player[] players;
    private Stage stage;
    private Table buttonsTable;
    private GameTextButton hitButton, jumpButton, leftButton, rightButton;

    public GameScreen(Game game) {
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
        //buttonsTable.setDebug(true);

        players = new Player[2];
        players[0] = new LocalHumanPlayer(hitButton, jumpButton, leftButton, rightButton);
        players[1] = new AIPlayer(gameWorld, 1);

        Gdx.input.setInputProcessor(stage);

        stage.addActor(gameWorld.getGroup());
        stage.addActor(buttonsTable);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Gdx.app.log("GameScreen", Integer.toString(buttonsTable.getZIndex()));
        //Gdx.app.log("GameScreen", Integer.toString(gameWorld.getGroup().getZIndex()));
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.app.log("GameScreen", Float.toString(hitButton.getX()));
        Gdx.app.log("GameScreen", Float.toString(hitButton.getY()));
        Gdx.app.log("GameScreen", Float.toString(jumpButton.getX()));
        Gdx.app.log("GameScreen", Float.toString(jumpButton.getY()));
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameWorld.update(delta, players[0].getMove(), players[1].getMove());
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
