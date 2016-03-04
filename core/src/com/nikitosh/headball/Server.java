package com.nikitosh.headball;

import com.badlogic.gdx.Gdx;
import java.net.*;
import java.io.*;

public final class Server {
    private Server() {}

    private static final int PORT = 12345;
    private static final String LOG_TAG = "MultiPlayerScreen";
    private static final String SERVER_WAITING_CLIENTS_MESSAGE = "Waiting for clients";
    private static final String CLIENTS_CONNECT_TO_SERVER_MESSAGE = "Got two clients";
    private static final String SERVER_START_ERROR_MESSAGE = "Starting server failed!";
    private static final String IOFLOWS_REDIRECT_ERROR_MESSAGE = "IOFlows redirection failed";

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Gdx.app.log(LOG_TAG, SERVER_WAITING_CLIENTS_MESSAGE);

            while (true) {
                Socket socketFirst = serverSocket.accept();
                Socket socketSecond = serverSocket.accept();
                (new DataOutputStream(socketFirst.getOutputStream())).writeByte(0);
                (new DataOutputStream(socketSecond.getOutputStream())).writeByte(1);
                Gdx.app.log(LOG_TAG, CLIENTS_CONNECT_TO_SERVER_MESSAGE);
                new Thread(new GameConnection(socketFirst, socketSecond)).start();
            }

        } catch (IOException e) {
            Gdx.app.log(LOG_TAG, SERVER_START_ERROR_MESSAGE, e);
        }
    }

    private static class GameConnection implements Runnable {

        private Socket socketFirst = null;
        private Socket socketSecond = null;
        private DataInputStream inFirst = null;
        private DataOutputStream outFirst = null;
        private DataInputStream inSecond = null;
        private DataOutputStream outSecond = null;

        public GameConnection(Socket s, Socket s2) throws IOException {
            socketFirst = s;
            socketSecond = s2;
            inFirst = new DataInputStream(socketFirst.getInputStream());
            outFirst = new DataOutputStream(socketFirst.getOutputStream());
            inSecond = new DataInputStream(socketSecond.getInputStream());
            outSecond = new DataOutputStream(socketSecond.getOutputStream());
        }

        private void redirectInputOutputFlows(DataInputStream in, DataOutputStream out) throws IOException {
            byte line = in.readByte();
            out.writeByte(line);
            out.flush();
        }

        public void run() {
            try {
                while (true) {
                    if (inFirst.available() > 0 && inSecond.available() > 0) {
                        redirectInputOutputFlows(inFirst, outSecond);
                        redirectInputOutputFlows(inSecond, outFirst);
                    }
                }
            } catch (IOException e) {
                Gdx.app.error(LOG_TAG, IOFLOWS_REDIRECT_ERROR_MESSAGE, e);
            }
        }

    }
}
