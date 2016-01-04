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

    private Table uiTable = new Table();
    private GameTextButtonTouchable hitButton;
    private Touchpad joystick;

    private Move move = new Move();

    public TouchpadInputController(Table infoTable) {
        hitButton = new GameTextButtonTouchable(HIT_BUTTON_NAME);
        hitButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                move.setHit(true);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                move.setHit(false);
            }
        });

        joystick = new Touchpad(TOUCHPAD_KNOB, new GameTouchpadStyle());

        uiTable.add(hitButton).left().bottom().pad(Constants.UI_ELEMENTS_INDENT);
        uiTable.add(infoTable).expand().bottom();
        uiTable.add(joystick).width(Constants.UI_LAYER_HEIGHT).height(Constants.UI_LAYER_HEIGHT).right().bottom();
    }

    @Override
    public Move getMove() {
        move.setRight(false);
        move.setLeft(false);
        move.setJump(false);
        if (joystick.getKnobPercentX() > 0) {
            move.setRight(true);
        }
        if (joystick.getKnobPercentX() < 0) {
            move.setLeft(true);
        }
        if (joystick.getKnobPercentY() >= 0.5f) {
            move.setJump(true);
        }
        return move;
    }

    @Override
    public Table getTable() {
        return uiTable;
    }
}
