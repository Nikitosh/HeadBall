package com.nikitosh.headball.players;

import com.nikitosh.headball.controllers.InputController;
import com.nikitosh.headball.Move;

public class LocalHumanPlayer implements Player {
    private InputController inputController;

    public LocalHumanPlayer(InputController inputController) {
        this.inputController = inputController;
    }

    @Override
    public Move getMove() {
        return inputController.getMove();
    }
}
