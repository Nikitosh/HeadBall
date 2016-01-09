package com.nikitosh.headball.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.ui.GameTouchpadStyle;
import com.nikitosh.headball.utils.Constants;

public class TouchpadInputController implements InputController {
    private final static String HIT_BUTTON_NAME = "Hit";
    private final static float TOUCHPAD_KNOB = 0;
    private final static float TOUCHPAD_JUMP_LEVEL_PERCENTAGE = 0.5f;

    private Table uiTable = new Table();
    private GameTextButtonTouchable hitButton;
    private Touchpad touchpad;

    private Move move = new Move();

    public TouchpadInputController(Table infoTable) {
        hitButton = new GameTextButtonTouchable(HIT_BUTTON_NAME);

        touchpad = new Touchpad(TOUCHPAD_KNOB, new GameTouchpadStyle());

        uiTable.add(hitButton).left().bottom().pad(Constants.UI_ELEMENTS_INDENT);
        uiTable.add(infoTable).expand().bottom();
        uiTable.add(touchpad).width(Constants.UI_LAYER_HEIGHT).height(Constants.UI_LAYER_HEIGHT).right().bottom();
    }

    @Override
    public Move getMove() {
        move.clear();
        if (hitButton.isPressed()) {
            move.setState(Constants.HIT, true);
        }
        if (touchpad.getKnobPercentX() > 0) {
            move.setState(Constants.RIGHT, true);
        }
        if (touchpad.getKnobPercentX() < 0) {
            move.setState(Constants.LEFT, true);
        }
        if (touchpad.getKnobPercentY() >= TOUCHPAD_JUMP_LEVEL_PERCENTAGE) {
            move.setState(Constants.JUMP, true);
        }
        return move;
    }

    @Override
    public Table getTable() {
        return uiTable;
    }
}
