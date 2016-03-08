package com.nikitosh.headball.players;

import com.nikitosh.headball.inputcontrollers.InputController;
import com.nikitosh.headball.Move;

public class LocalHumanPlayer implements Player {
    private final InputController inputController;

    public LocalHumanPlayer(InputController inputController) {
        this.inputController = inputController;
    }

    @Override
    public Move getMove() {
        return inputController.getMove();
    }
}
