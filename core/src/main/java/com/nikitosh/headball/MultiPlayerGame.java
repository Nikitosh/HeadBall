package com.nikitosh.headball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.jsonReaders.LevelReader;
import com.nikitosh.headball.players.Player;
import com.nikitosh.headball.players.RemoteHumanPlayer;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.GameSettings;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class MultiPlayerGame implements Runnable {
    private static final String LOG_TAG = "MultiPlayerGame";
    private static final String RUN_ERROR_MESSAGE = "Run failed!";

    private final DatagramChannel firstPlayerChannel;
    private final DatagramChannel secondPlayerChannel;
    private final SocketAddress firstSocketAddress;
    private final SocketAddress secondSocketAddress;

    private static final int MILLISECONDS = 1000;
    private long startTime = System.currentTimeMillis();

    private final Move[] lastMove = new Move[Constants.PLAYERS_NUMBER];

    private final GameWorld gameWorld;
    private final Array<Player> players = new Array<>();

    public MultiPlayerGame(MatchInfo matchInfo, DatagramChannel firstPlayerChannel,
                           DatagramChannel secondPlayerChannel, SocketAddress firstSocketAddress,
                           SocketAddress secondSocketAddress) throws IOException {
        AssetLoader.loadFont();
        AssetLoader.load();

        gameWorld = LevelReader.loadLevel(matchInfo.getLevelNumber());
        gameWorld.setDrawResultPossible(matchInfo.isDrawResultPossible());

        this.firstPlayerChannel = firstPlayerChannel;
        this.secondPlayerChannel = secondPlayerChannel;
        this.firstSocketAddress = firstSocketAddress;
        this.secondSocketAddress = secondSocketAddress;

        firstPlayerChannel.send(ByteBuffer.wrap(("0").getBytes()), firstSocketAddress);
        secondPlayerChannel.send(ByteBuffer.wrap(("1").getBytes()), secondSocketAddress);


        initializePlayers();
    }

    private void initializePlayers() {
        players.add(new RemoteHumanPlayer(firstPlayerChannel));
        players.add(new RemoteHumanPlayer(secondPlayerChannel));
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
        gameWorldFrameSerialization += Integer.toString(
                (int) (GameSettings.getInteger(Constants.GAME_DURATION)
                        - gameWorld.getGameDuration())) + '\n';
        return gameWorldFrameSerialization;
    }

    private void sendGameWorldFrame(DatagramChannel channel, SocketAddress socketAddress) {
        try {
            channel.send(ByteBuffer.wrap(serializeGameWorldFrame().getBytes()), socketAddress);
        } catch (IOException e) {
            Gdx.app.error(LOG_TAG, RUN_ERROR_MESSAGE, e);
        }
    }


    @Override
    public void run() {
        while (!gameWorld.isEnded()) {
            long start = System.currentTimeMillis();
            sendGameWorldFrame(firstPlayerChannel, firstSocketAddress);
            sendGameWorldFrame(secondPlayerChannel, secondSocketAddress);
            try {
                Thread.sleep(MILLISECONDS / Constants.FRAMES_PER_SECOND + System.currentTimeMillis() - start);
            } catch (InterruptedException e) {
                Gdx.app.error(LOG_TAG, "", e);
            }
            for (int i = 0; i < lastMove.length; i++) {
                Move currentMove = players.get(i).getMove();
                if (currentMove != null) {
                    lastMove[i] = currentMove;
                }
            }
            gameWorld.update(getDelta(), lastMove[0], lastMove[1]);
        }
        try {
            firstPlayerChannel.send(ByteBuffer.wrap(
                    Boolean.toString(gameWorld.isEnded()).getBytes()), firstSocketAddress);
            secondPlayerChannel.send(ByteBuffer.wrap(
                    Boolean.toString(gameWorld.isEnded()).getBytes()), secondSocketAddress);
        } catch (IOException e) {
            Gdx.app.error(LOG_TAG, "", e);
        }
        close();
    }

    private void close() {
        firstPlayerChannel.socket().close();
        secondPlayerChannel.socket().close();
    }

}
