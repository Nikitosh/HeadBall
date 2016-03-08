package com.nikitosh.headball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import java.net.*;
import java.io.*;

public final class Server extends Game {

    private static final int PORT = 12345;
    private static final String LOG_TAG = "MultiPlayerScreen";
    private static final String SERVER_WAITING_CLIENTS_MESSAGE = "Waiting for clients";
    private static final String CLIENTS_CONNECT_TO_SERVER_MESSAGE = "Got two clients";
    private static final String SERVER_START_ERROR_MESSAGE = "Starting server failed!";

    private static ActionResolver actionResolver;

    public Server(ActionResolver actionResolver) {
        super();
        Server.actionResolver = actionResolver;
    }

    public static void main() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Gdx.app.log(LOG_TAG, SERVER_WAITING_CLIENTS_MESSAGE);

            while (true) {
                Socket socketFirst = serverSocket.accept();
                Socket socketSecond = serverSocket.accept();
                Gdx.app.log(LOG_TAG, CLIENTS_CONNECT_TO_SERVER_MESSAGE);
                new Thread(new MultiPlayerGame((new MatchInfo(new Team("", ""),
                        new Team("", ""), false, false)), socketFirst, socketSecond)).start();
            }

        } catch (IOException e) {
            Gdx.app.error(LOG_TAG, SERVER_START_ERROR_MESSAGE, e);
        }
    }

    @Override
    public void create() {
        actionResolver.signIn();
        main();
    }
}
