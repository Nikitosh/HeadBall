package com.nikitosh.headball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nikitosh.headball.ActionResolverSingleton;
import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.gamecontrollers.GameController;
import com.nikitosh.headball.gamecontrollers.MultiPlayerGameController;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.ScreenManager;

import java.io.DataInput;
import java.io.DataInputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class MultiPlayerWaitingScreen extends BackgroundStageAbstractScreen {
    private static final int PORT = 12345;
    private static final String SERVER_ADDRESS = "5.19.205.147";

    private static final String LOG_TAG = "MultiPlayerWaitingScreen";
    private static final String CONNECTION_ERROR_MESSAGE = "Connection failed!";
    private static final String RECONNECT = "Check for your Internet access and try to reconnect later";

    private Socket socket;

    private void closeSocket() {
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                Gdx.app.error(LOG_TAG, "", e);
            }
        }
    }

    public MultiPlayerWaitingScreen() {
        Table table = new Table();
        table.add(new Label("Wait for a while...", AssetLoader.getGameSkin(), "background"));
        stack.addActor(table);
    }

    @Override
    public void show() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress ipAddress = InetAddress.getByName(SERVER_ADDRESS);
                    Gdx.app.log(LOG_TAG, "Any of you heard of a socket with IP address "
                            + SERVER_ADDRESS + " and port " + PORT + "?");

                    Socket socket = new Socket(ipAddress, PORT);
                    int channelPort = new DataInputStream(socket.getInputStream()).readInt();
                    Gdx.app.log(LOG_TAG,  channelPort + "  Yes! I just got hold of the program.");

                    DatagramChannel channel = DatagramChannel.open();
                    channel.socket().bind(new InetSocketAddress(0));
                    channel.send(ByteBuffer.wrap(("Hello Server").getBytes()),
                            new InetSocketAddress(ipAddress, channelPort));

                    ScreenManager.getInstance().disposeCurrentScreen();
                    GameScreen gameScreen = new GameScreen();
                    Gdx.app.log(LOG_TAG, "Create MultiPlayerGameController\n");
                    GameController gameController = new MultiPlayerGameController(gameScreen,
                            new MatchInfo(new Team("", ""), new Team("", ""), false, false),
                            channel, new InetSocketAddress(ipAddress, channelPort));
                    ScreenManager.getInstance().setScreen(gameScreen);
                } catch (Exception e) {
                    Gdx.app.error(LOG_TAG, CONNECTION_ERROR_MESSAGE, e);
                    ActionResolverSingleton.getInstance().showToast(RECONNECT);
                    closeSocket();
                    ScreenManager.getInstance().disposeCurrentScreen();
                }
            }
        });

    }
}
