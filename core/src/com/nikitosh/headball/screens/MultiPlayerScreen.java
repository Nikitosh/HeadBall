package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.nikitosh.headball.players.LocalHumanPlayer;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.players.RemoteHumanPlayer;

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
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Move myMove = players[playerNumber].getMove();
        myMove.serialize(out);
        try {
            out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (gameState == GameState.GAME_RUNNING) {
            if (playerNumber == 0) {
                gameWorld.update(delta, myMove, players[1].getMove());
            }
            else {
                gameWorld.update(delta, players[0].getMove(), myMove);
            }
        }
        stage.act(delta);
        stage.draw();
    }
}
