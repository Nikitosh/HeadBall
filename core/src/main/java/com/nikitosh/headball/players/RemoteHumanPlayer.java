package com.nikitosh.headball.players;

import com.nikitosh.headball.Move;

import java.nio.channels.DatagramChannel;

public class RemoteHumanPlayer implements Player {
    private final DatagramChannel datagramChannel;

    public RemoteHumanPlayer(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }

    @Override
    public Move getMove() {
        return Move.deserialize(datagramChannel);
    }
}
