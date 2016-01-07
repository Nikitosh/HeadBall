package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.players.RemoteHumanPlayer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class MultiPlayerScreen extends GameScreen {
    private static final String LOG_TAG = "MultiPlayerScreen";

    private static final int PORT = 2345;
    private static final String SERVER_ADDRESS = "192.168.43.9";
    private DataInputStream in;
    private DataOutputStream out;

    public MultiPlayerScreen(Game game, Team firstTeam, Team secondTeam, Screen previousScreen) {
        super(game, firstTeam, secondTeam, previousScreen);
        try {
            InetAddress ipAddress = InetAddress.getByName(SERVER_ADDRESS);
            Gdx.app.log(LOG_TAG, "Any of you heard of a socket with IP address " + SERVER_ADDRESS + " and port " + PORT + "?");
            Socket socket = new Socket(ipAddress, PORT);
            Gdx.app.log(LOG_TAG, "Yes! I just got hold of the program.");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            String s = in.readUTF();
            Gdx.app.log(LOG_TAG, s);
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
        players[1 - playerNumber] = new RemoteHumanPlayer(in);
        super.initializePlayers();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Move playerMove = players[playerNumber].getMove();
        playerMove.serialize(out);
        try {
            out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (gameState == GameState.GAME_RUNNING) {
            if (playerNumber == 0) {
                gameWorld.update(delta, playerMove, players[1].getMove());
            }
            else {
                gameWorld.update(delta, players[0].getMove(), playerMove);
            }
        }
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void restartGame() {
        dispose();
        game.setScreen(new MultiPlayerScreen(game, firstTeam, secondTeam, previousScreen));
    }
}
