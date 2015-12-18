package com.nikitosh.headball.players;

import com.nikitosh.headball.Move;

import java.io.DataInputStream;

public class RemoteHumanPlayer extends Player {
    private DataInputStream inputStream;

    public RemoteHumanPlayer(DataInputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Move getMove() {
        return Move.deserialize(inputStream);
    }
}
