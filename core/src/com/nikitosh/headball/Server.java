package com.nikitosh.headball;

import java.net.*;
import java.io.*;

public class Server {

    public static void main(String[] args) {

        try {
            int port = 2345;
            ServerSocket serverSocket = new ServerSocket(port);
            System.err.println("Waiting for a clients...");

            while(true) {
                Socket socketFirst = serverSocket.accept();
                (new DataOutputStream(socketFirst.getOutputStream())).writeUTF("0\n");
                Socket socketSecond = serverSocket.accept();
                (new DataOutputStream(socketSecond.getOutputStream())).writeUTF("1\n");
                System.err.println("Got two clients :)");
                System.out.println();
                new Thread(new GameConnection(socketFirst, socketSecond)).start();
            }
        } catch(Exception x) { x.printStackTrace(); }
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