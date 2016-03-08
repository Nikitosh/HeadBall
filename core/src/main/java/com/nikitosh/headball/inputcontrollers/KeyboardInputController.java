package com.nikitosh.headball.inputcontrollers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.nikitosh.headball.Move;

public class KeyboardInputController extends InputController{
    private static final int[] KEYS = {Input.Keys.SPACE, Input.Keys.UP, Input.Keys.LEFT, Input.Keys.RIGHT};

    private Move move = new Move();

    public KeyboardInputController() {
        super();
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
}
