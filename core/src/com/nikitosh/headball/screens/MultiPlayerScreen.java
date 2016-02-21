package com.nikitosh.headball.screens;

import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.players.RemoteHumanPlayer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class MultiPlayerScreen extends GameScreen {
    private DataInputStream in;
    private DataOutputStream out;

    public MultiPlayerScreen(MatchInfo matchInfo, int playerNumber, DataInputStream in, DataOutputStream out) {
        super(matchInfo);
        this.in = in;
        this.out = out;
        this.playerNumber = playerNumber;
        initializePlayers();
    }

    @Override
    protected void initializePlayers() {
        players[1 - playerNumber] = new RemoteHumanPlayer(in);
        super.initializePlayers();
    }

    @Override
    public void render(float delta) {
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
        super.render(delta);
    }
}
