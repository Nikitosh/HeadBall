package com.nikitosh.headball;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Move {

    private boolean[] footballerState = new boolean[4];

    public Move() {
        clear();
    }

    public Move(boolean[] footballerState) {
        System.arraycopy(footballerState, 0, this.footballerState, 0, footballerState.length);
    }

    public boolean getState(int i) {
        return footballerState[i];
    }

    public void setState(int i, boolean st) {
        footballerState[i] = st;
    }

    public void clear() {
        for (int i = 0; i < 4; i++) {
            footballerState[i] = false;
        }
    }

    public void serialize(DataOutputStream outputStream) {
        try {
            byte message = 0;
            for (int i = 0; i < 4; i++)
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
            for (int i = 0; i < 4; i++) {
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
