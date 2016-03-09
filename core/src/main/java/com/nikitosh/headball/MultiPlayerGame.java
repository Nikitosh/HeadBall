package com.nikitosh.headball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.jsonReaders.LevelReader;
import com.nikitosh.headball.players.Player;
import com.nikitosh.headball.players.RemoteHumanPlayer;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MultiPlayerGame implements Runnable {

    private final Socket firstPlayerSocket;
    private final Socket secondPlayerSocket;
    private final DataInputStream firstPlayerInputStream;
    private final DataInputStream secondPlayerInputStream;
    private final DataOutputStream firstPlayerOutputStream;
    private final DataOutputStream secondPlayerOutputStream;

    private static final int MILLISECONDS = 1000;
    private long startTime = System.currentTimeMillis();

    private static final String RUN_ERROR_MESSAGE = "Run failed!";
    private static final String CLOSE_RESOURCES_ERROR_MESSAGE = "Closing resources failed!";

    private final GameWorld gameWorld;
    private final Array<Player> players = new Array<>();

    private static final String LOG_TAG = "MultiPlayerGame";

    public MultiPlayerGame(MatchInfo matchInfo, Socket firstPlayerSocket,
                           Socket secondPlayerSocket) throws IOException {
        AssetLoader.loadFont();
        AssetLoader.load();

        gameWorld = LevelReader.loadLevel(matchInfo.getLevelNumber(), true);
        gameWorld.setDrawResultPossible(matchInfo.isDrawResultPossible());

        this.firstPlayerSocket = firstPlayerSocket;
        this.secondPlayerSocket = secondPlayerSocket;
        this.firstPlayerInputStream = new DataInputStream(firstPlayerSocket.getInputStream());
        this.secondPlayerInputStream = new DataInputStream(secondPlayerSocket.getInputStream());
        this.firstPlayerOutputStream = new DataOutputStream(firstPlayerSocket.getOutputStream());
        this.secondPlayerOutputStream = new DataOutputStream(secondPlayerSocket.getOutputStream());

        firstPlayerOutputStream.writeUTF("0\n");
        secondPlayerOutputStream.writeUTF("1\n");
        firstPlayerOutputStream.flush();
        secondPlayerOutputStream.flush();

        initializePlayers();
    }

    private void initializePlayers() {
        players.add(new RemoteHumanPlayer(firstPlayerInputStream));
        players.add(new RemoteHumanPlayer(secondPlayerInputStream));
    }

    private float getDelta() {
        long diff = System.currentTimeMillis() - startTime;
        long targetDelay = MILLISECONDS / Constants.FRAMES_PER_SECOND;
        if (diff < targetDelay) {
            try {
                Thread.sleep(targetDelay - diff);
            } catch (InterruptedException e) {
                Gdx.app.error(LOG_TAG, "", e);
            }
        }
        long currentTime = System.currentTimeMillis();
        float delta = (currentTime - startTime) / (float) MILLISECONDS;
        startTime = currentTime;
        return delta;
    }

    private String serializeGameWorldFrame() {
        String gameWorldFrameSerialization = "";
        gameWorldFrameSerialization += Boolean.toString(gameWorld.isEnded()) + Constants.DATA_SEPARATOR;
        gameWorldFrameSerialization += gameWorld.getBall().serialise();
        for (int i = 0; i < gameWorld.getFootballers().length; i++) {
            gameWorldFrameSerialization += gameWorld.getFootballers()[i].serialise();
            Body leg = gameWorld.getFootballers()[i].getLeg();
            gameWorldFrameSerialization += Float.toString(leg.getPosition().x) + ' '
                    + Float.toString(leg.getPosition().y) + ' '
                    + Float.toString(leg.getAngle()) + Constants.DATA_SEPARATOR;
        }
        String scoreSerialization = "";
        for (int i = 0; i < gameWorld.getScore().length; i++) {
            scoreSerialization += (Integer.toString(gameWorld.getScore()[i]) + ' ');
        }
        gameWorldFrameSerialization += scoreSerialization + Constants.DATA_SEPARATOR;
        gameWorldFrameSerialization += Integer.toString((int) gameWorld.getGameDuration()) + '\n';
        return gameWorldFrameSerialization;
    }

    private void sendGameWorldFrame(DataOutputStream outputStream) {
        try {
            outputStream.writeUTF(serializeGameWorldFrame());
            outputStream.flush();
        } catch (IOException e) {
            Gdx.app.error(LOG_TAG, RUN_ERROR_MESSAGE, e);
        }
    }


    @Override
    public void run() {
        while (!gameWorld.isEnded()) {
            sendGameWorldFrame(firstPlayerOutputStream);
            sendGameWorldFrame(secondPlayerOutputStream);
            gameWorld.update(getDelta(),
                    players.get(0).getMove(), players.get(1).getMove());
        }
        try {
            firstPlayerOutputStream.writeUTF(Boolean.toString(gameWorld.isEnded()));
            firstPlayerOutputStream.flush();
            secondPlayerOutputStream.writeUTF(Boolean.toString(gameWorld.isEnded()));
            secondPlayerOutputStream.flush();
        } catch (IOException e) {
            Gdx.app.error(LOG_TAG, "", e);
        }
        close();
    }

    private void close() {
        try {
            firstPlayerSocket.close();
            secondPlayerSocket.close();
        } catch (IOException e) {
            Gdx.app.error(LOG_TAG, CLOSE_RESOURCES_ERROR_MESSAGE, e);
        }
    }

}
