package com.nikitosh.headball.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nikitosh.headball.Move;

public class KeyboardInputController implements InputController{
    private Table infoTable;
    private Move move = new Move();

    public KeyboardInputController(Table infoTable) {
        this.infoTable = infoTable;
    }

    @Override
    public Move getMove() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            move.setLeft(true);
        }
        else {
            move.setLeft(false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            move.setRight(true);
        }
        else {
            move.setRight(false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            move.setJump(true);
        }
        else {
            move.setJump(false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            move.setHit(true);
        }
        else {
            move.setHit(false);
        }
        return move;
    }

    @Override
    public Table getTable() {
        return infoTable;
    }
}
