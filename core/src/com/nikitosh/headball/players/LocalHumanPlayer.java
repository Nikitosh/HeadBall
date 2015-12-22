package com.nikitosh.headball.players;

import com.nikitosh.headball.InputController;
import com.nikitosh.headball.Move;

public class LocalHumanPlayer extends Player {
    private InputController inputController;

    public LocalHumanPlayer(InputController inputController) {
        this.inputController = inputController;
    }

    @Override
    public Move getMove() {
        return inputController.getMove();
    }
}
