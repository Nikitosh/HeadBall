package com.nikitosh.headball.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.ui.GameTextButton;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.utils.Constants;

public class ButtonsInputController implements InputController {

    private final static String[] BUTTONS_NAMES = {"Hit", "Jump", "Left", "Right"};

    private Table uiTable = new Table();
    private Array<GameTextButtonTouchable> buttonsArray = new Array<>();

    private Move move = new Move();

    public ButtonsInputController(Table infoTable) {

        for (String name: BUTTONS_NAMES) {
            buttonsArray.add(new GameTextButtonTouchable(name));
        }

        uiTable.add(buttonsArray.get(Constants.HIT)).expand().bottom();
        uiTable.add(buttonsArray.get(Constants.JUMP)).expand().bottom();
        uiTable.add(infoTable).expand().bottom();
        uiTable.add(buttonsArray.get(Constants.LEFT)).expand().bottom();
        uiTable.add(buttonsArray.get(Constants.RIGHT)).expand().bottom();
    }

    @Override
    public Move getMove() {
        move.clear();
        for (int i = 0; i < buttonsArray.size; i++) {
            if (buttonsArray.get(i).isPressed()) {
                move.setState(i, true);
            }
        }
        return move;
    }

    @Override
    public Table getTable() {
        return uiTable;
    }
}
