package com.nikitosh.headball;

import java.net.*;
import java.io.*;

public final class Server {
    private Server() {}

    private static final String WAITING_CLIENTS = "Waiting for clients";
    private static final String CONNECTION_GOT = "Got two clients";
    private static final int PORT = 2345;

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.err.println(WAITING_CLIENTS);

            while (true) {
                Socket socketFirst = serverSocket.accept();
                (new DataOutputStream(socketFirst.getOutputStream())).writeUTF("0\n");
                Socket socketSecond = serverSocket.accept();
                (new DataOutputStream(socketSecond.getOutputStream())).writeUTF("1\n");
                System.err.println(CONNECTION_GOT);
                new Thread(new GameConnection(socketFirst, socketSecond)).start();
            }
        } catch (Exception x) {
            x.printStackTrace();
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
            String line = in.readUTF();
            out.writeUTF(line);
            out.flush();
        }

        public void run() {
            try {
                while (true) {
                    redirectInputOutputFlows(inFirst, outSecond);
                    redirectInputOutputFlows(inSecond, outFirst);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
