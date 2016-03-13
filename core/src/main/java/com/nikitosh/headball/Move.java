package com.nikitosh.headball;

import com.badlogic.gdx.Gdx;
import com.nikitosh.headball.utils.Constants;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;


public class Move {
    private static final int STATE_NUMBER = 4;
    private static final String LOG_TAG = "Move";
    private static final String MOVE_SERIALIZE_ERROR_MESSAGE = "Serialization failed!";
    private static final String MOVE_DESERIALIZE_ERROR_MESSAGE = "Deserialization failed!";

    private final boolean[] footballerState = new boolean[STATE_NUMBER];

    public Move() {
        clear();
    }

    public Move(boolean[] footballerState) {
        System.arraycopy(footballerState, 0, this.footballerState, 0, footballerState.length);
    }

    public boolean getState(int i) {
        return footballerState[i];
    }

    public void setState(int i, boolean state) {
        footballerState[i] = state;
    }

    public void clear() {
        for (int i = 0; i < STATE_NUMBER; i++) {
            footballerState[i] = false;
        }
    }

    public byte[] serialize() {
        byte[] message = new byte[STATE_NUMBER];
        message[0] = 0;
        for (int i = 0; i < STATE_NUMBER; i++) {
            if (footballerState[i]) {
                message[0] |= 1 << i;
            }
        }
        return message;
    }

    public static Move deserialize(DatagramChannel channel) {
        byte[] message = null;
        ByteBuffer bb = ByteBuffer.allocate(Constants.BUFFER_SIZE);
        bb.clear();
        try {
            for (int i = 0; i < Constants.FRAMES_TO_SKIP_NUMBER; i++) {
                if (channel.receive(bb) != null) {
                    bb.flip();
                    message = new byte[bb.limit()];
                    bb.get(message);
                    bb.clear();
                }
            }
        } catch (IOException e) {
            Gdx.app.error(LOG_TAG, "", e);
        }
        if (message == null) {
            Gdx.app.error(LOG_TAG, "Receiving Move failed!");
            return null;
        }
        Move move = new Move();
        for (int i = 0; i < STATE_NUMBER; i++) {
            if (message[0] % 2 == 1) {
                move.setState(i, true);
            }
            message[0] /= 2;
        }
        return move;
    }
}
