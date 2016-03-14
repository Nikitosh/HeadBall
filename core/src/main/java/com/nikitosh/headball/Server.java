package com.nikitosh.headball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.nikitosh.headball.utils.Constants;

import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public final class Server extends Game {

    private static final int PORT = 12345;
    private static final String LOG_TAG = "MultiPlayerScreen";
    private static final String SERVER_WAITING_CLIENTS_MESSAGE = "Waiting for clients";
    private static final String CLIENTS_CONNECT_TO_SERVER_MESSAGE = "Got two clients";
    private static final String SERVER_START_ERROR_MESSAGE = "Starting server failed!";

    private static void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            int currentClientPort = Constants.FIRST_CLIENT_PORT;
            Gdx.app.log(LOG_TAG, SERVER_WAITING_CLIENTS_MESSAGE);
            byte[] receiveData = new byte[Constants.BUFFER_SIZE];
            while (currentClientPort < Constants.LAST_CLIENT_PORT) {

                int firstClientPort = currentClientPort++;
                DatagramChannel firstChannel = DatagramChannel.open();
                firstChannel.socket().bind(new InetSocketAddress(firstClientPort));
                int secondClientPort = currentClientPort++;
                DatagramChannel secondChannel = DatagramChannel.open();
                secondChannel.socket().bind(new InetSocketAddress(secondClientPort));

                Socket firstSocket = serverSocket.accept();
                Socket secondSocket = serverSocket.accept();
                new DataOutputStream(firstSocket.getOutputStream()).writeInt(firstClientPort);
                new DataOutputStream(secondSocket.getOutputStream()).writeInt(secondClientPort);

                ByteBuffer bbFirst = ByteBuffer.allocate(Constants.BUFFER_SIZE);
                SocketAddress firstSocketAddress = firstChannel.receive(bbFirst);
                ByteBuffer bbSecond = ByteBuffer.allocate(Constants.BUFFER_SIZE);
                SocketAddress secondSocketAddress = secondChannel.receive(bbSecond);

                new Thread(new MultiPlayerGame((new MatchInfo(new Team("", ""),
                        new Team("", ""), false, false)), firstChannel,
                        secondChannel, firstSocketAddress, secondSocketAddress)).start();
            }

        } catch (IOException e) {
            Gdx.app.error(LOG_TAG, SERVER_START_ERROR_MESSAGE, e);
        }
    }

    @Override
    public void create() {
        run();
    }
}
