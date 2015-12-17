package com.nikitosh.headball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class MultiPlayerScreen implements Screen {

    private static final int PORT = 2345;
    private static final String SERVER_ADDRESS = "5.19.205.147";
    private DataInputStream in;
    private DataOutputStream out;

    private final Game game;
    private GameWorld gameWorld;
    private Player[] players;
    private Stage stage;
    private Table buttonsTable;
    private GameTextButton hitButton, jumpButton, leftButton, rightButton;
    private int playerNumber;

    public MultiPlayerScreen(Game game) {

        try {
            InetAddress ipAddress = InetAddress.getByName(SERVER_ADDRESS);
            System.out.println("Any of you heard of a socket with IP address " + SERVER_ADDRESS + " and port " + PORT + "?");
            Socket socket = new Socket(ipAddress, PORT);
            System.out.println("Yes! I just got hold of the program.");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            String s = in.readUTF();
            Gdx.app.log("MultiPlayer", s);
            if (s.charAt(0) == '0') {
                playerNumber = 0;
            } else {
                playerNumber = 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.game = game;
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
        players[playerNumber] = new LocalHumanPlayer(hitButton, jumpButton, leftButton, rightButton);
        players[1 - playerNumber] = new RemoteHumanPlayer(in);

        Gdx.input.setInputProcessor(stage);

        stage.addActor(gameWorld.getGroup());
        stage.addActor(buttonsTable);

    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.app.log("Multi", "render1");
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.app.log("Multi", "render2");
        Move myMove = players[playerNumber].getMove();
        Gdx.app.log("Multi", "render3");
        myMove.serialize(out);
        try {
            out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Gdx.app.log("Multi", "render4");
        if (playerNumber == 0) {
            gameWorld.update(delta, myMove, players[1].getMove());
        }
        else {
            gameWorld.update(delta, players[0].getMove(), myMove);
        }
        Gdx.app.log("Multi", "render5");
        stage.act(delta);
        Gdx.app.log("Multi", "render6");
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
