package com.nikitosh.headball;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Move {
    private static final int STATE_NUMBER = 4;

    private boolean[] footballerState = new boolean[STATE_NUMBER];

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

    public void serialize(DataOutputStream outputStream) {
        try {
            byte message = 0;
            for (int i = 0; i < STATE_NUMBER; i++)
                if (footballerState[i]) {
                    message |= 1 << i;
                }
            outputStream.write(message);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Move deserialize(DataInputStream inputStream) {
        try {
            byte message = (byte) inputStream.read();
            Move move = new Move();
            for (int i = 0; i < STATE_NUMBER; i++) {
                if (message % 2 == 1) {
                    move.setState(i, true);
                }
                message /= 2;
            }
            return move;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
