package com.nikitosh.headball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.nikitosh.headball.utils.Constants;

import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public final class Server extends Game {

    private static final int PORT = 12346;
    private static final String LOG_TAG = "MultiPlayerScreen";
    private static final String SERVER_WAITING_CLIENTS_MESSAGE = "Waiting for clients";
    private static final String CLIENTS_CONNECT_TO_SERVER_MESSAGE = "Got two clients";
    private static final String SERVER_START_ERROR_MESSAGE = "Starting server failed!";

    private static void run() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(PORT);
            int currentClientPort = Constants.FIRST_CLIENT_PORT;
            Gdx.app.log(LOG_TAG, SERVER_WAITING_CLIENTS_MESSAGE);
            while (currentClientPort < Constants.LAST_CLIENT_PORT) {


                DatagramChannel firstChannel = getChannel(serverSocket, currentClientPort);
                currentClientPort++;

                ByteBuffer bbFirst = ByteBuffer.allocate(Constants.BUFFER_SIZE);
                SocketAddress firstSocketAddress = firstChannel.receive(bbFirst);

                DatagramChannel secondChannel = getChannel(serverSocket, currentClientPort);
                currentClientPort++;
                Gdx.app.log(LOG_TAG, CLIENTS_CONNECT_TO_SERVER_MESSAGE);

                ByteBuffer bbSecond = ByteBuffer.allocate(Constants.BUFFER_SIZE);
                SocketAddress secondSocketAddress = secondChannel.receive(bbSecond);

                firstChannel.configureBlocking(false);
                secondChannel.configureBlocking(false);

                new Thread(new MultiPlayerGame((new MatchInfo(new Team("", ""),
                        new Team("", ""), false, false)), firstChannel,
                        secondChannel, firstSocketAddress, secondSocketAddress)).start();
            }

        } catch (IOException e) {
            Gdx.app.error(LOG_TAG, SERVER_START_ERROR_MESSAGE, e);
        }
    }

    private static DatagramChannel getChannel(
            DatagramSocket serverSocket, int currentClientPort) throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(currentClientPort));

        byte[] receiveData = new byte[Constants.BUFFER_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);

        SocketAddress socketAddress = receivePacket.getSocketAddress();
        DatagramPacket sendPacket = new DatagramPacket(Integer.toString(currentClientPort).getBytes(),
                Integer.toString(currentClientPort).getBytes().length, socketAddress);
        serverSocket.send(sendPacket);

        return channel;
    }

    @Override
    public void create() {
        run();
    }
}
