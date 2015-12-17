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

public class MultiPlayerScreen extends GameScreen {

    private static final int PORT = 2345;
    private static final String SERVER_ADDRESS = "192.168.43.9";
    private DataInputStream in;
    private DataOutputStream out;

    private int playerNumber;

    public MultiPlayerScreen(Game game) {
        super(game);
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
        initializePlayers();
    }

    @Override
    protected void initializePlayers() {
        players[playerNumber] = new LocalHumanPlayer(hitButton, jumpButton, leftButton, rightButton);
        players[1 - playerNumber] = new RemoteHumanPlayer(in);
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
        if (gameState == GameState.GAME_RUNNING) {
            if (playerNumber == 0) {
                gameWorld.update(delta, myMove, players[1].getMove());
            }
            else {
                gameWorld.update(delta, players[0].getMove(), myMove);
            }
        }
        Gdx.app.log("Multi", "render5");
        stage.act(delta);
        Gdx.app.log("Multi", "render6");
        stage.draw();
    }
}
