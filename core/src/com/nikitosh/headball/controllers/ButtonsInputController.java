package com.nikitosh.headball.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.ui.GameTextButtonTouchable;

public class ButtonsInputController implements InputController {
    private final static String HIT_BUTTON_NAME = "Hit";
    private final static String JUMP_BUTTON_NAME = "Jump";
    private final static String LEFT_BUTTON_NAME = "Left";
    private final static String RIGHT_BUTTON_NAME = "Right";

    private Table uiTable = new Table();

    private Move move = new Move();

    public ButtonsInputController(Table infoTable) {
        GameTextButtonTouchable hitButton, jumpButton, leftButton, rightButton;

        hitButton = new GameTextButtonTouchable(HIT_BUTTON_NAME);
        jumpButton = new GameTextButtonTouchable(JUMP_BUTTON_NAME);
        leftButton = new GameTextButtonTouchable(LEFT_BUTTON_NAME);
        rightButton = new GameTextButtonTouchable(RIGHT_BUTTON_NAME);

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
        jumpButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                move.setJump(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                move.setJump(false);
            }
        });
        leftButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                move.setLeft(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                move.setLeft(false);
            }
        });
        rightButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                move.setRight(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                move.setRight(false);
            }
        });

        uiTable.add(hitButton).expand().bottom();
        uiTable.add(jumpButton).expand().bottom();
        uiTable.add(infoTable).expand().bottom();
        uiTable.add(leftButton).expand().bottom();
        uiTable.add(rightButton).expand().bottom();
    }

    @Override
    public Move getMove() {
        return move;
    }

    @Override
    public Table getTable() {
        return uiTable;
    }
}
