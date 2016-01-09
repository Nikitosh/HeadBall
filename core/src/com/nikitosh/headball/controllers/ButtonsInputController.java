package com.nikitosh.headball.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.ui.GameTextButton;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.utils.Constants;

public class ButtonsInputController implements InputController {
    private final static String HIT_BUTTON_NAME = "Hit";
    private final static String JUMP_BUTTON_NAME = "Jump";
    private final static String LEFT_BUTTON_NAME = "Left";
    private final static String RIGHT_BUTTON_NAME = "Right";


    private Table uiTable = new Table();
    private GameTextButtonTouchable[] buttonsArray = null;

    private Move move = new Move();

    public ButtonsInputController(Table infoTable) {
        buttonsArray = new GameTextButtonTouchable[]{new GameTextButtonTouchable(HIT_BUTTON_NAME),
                new GameTextButtonTouchable(JUMP_BUTTON_NAME),
                new GameTextButtonTouchable(LEFT_BUTTON_NAME),
                new GameTextButtonTouchable(RIGHT_BUTTON_NAME)};

        uiTable.add(buttonsArray[Constants.HIT]).expand().bottom();
        uiTable.add(buttonsArray[Constants.JUMP]).expand().bottom();
        uiTable.add(infoTable).expand().bottom();
        uiTable.add(buttonsArray[Constants.LEFT]).expand().bottom();
        uiTable.add(buttonsArray[Constants.RIGHT]).expand().bottom();
    }

    @Override
    public Move getMove() {
        move.clear();
        for (int i = 0; i < buttonsArray.length; i++) {
            if (buttonsArray[i].isPressed()) {
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
