package com.nikitosh.headball.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nikitosh.headball.Move;

public class KeyboardInputController implements InputController{
    private Table infoTable;
    private Move move = new Move();

    private static final int[] KEYS = {Input.Keys.SPACE, Input.Keys.UP, Input.Keys.LEFT, Input.Keys.RIGHT};

    public KeyboardInputController(Table infoTable) {
        this.infoTable = infoTable;
    }

    @Override
    public Move getMove() {
        for (int i = 0; i < KEYS.length; i++) {
            if (Gdx.input.isKeyPressed(KEYS[i])) {
                move.setState(i, true);
            } else {
                move.setState(i, false);
            }
        }
        return move;
    }

    @Override
    public Table getTable() {
        return infoTable;
    }
}
