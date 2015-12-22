package com.nikitosh.headball;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nikitosh.headball.ui.GameTextButtonTouchable;

public class ButtonsInputController implements InputController {
    private Table uiTable = new Table();

    private Move move = new Move();

    public ButtonsInputController() {
        GameTextButtonTouchable hitButton, jumpButton, leftButton, rightButton;

        hitButton = new GameTextButtonTouchable("Hit");
        jumpButton = new GameTextButtonTouchable("Jump");
        leftButton = new GameTextButtonTouchable("Left");
        rightButton = new GameTextButtonTouchable("Right");

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

        uiTable.add(hitButton).expand().fillX().bottom();
        uiTable.add(jumpButton).expand().fillX().bottom();
        uiTable.add(leftButton).expand().fillX().bottom();
        uiTable.add(rightButton).expand().fillX().bottom();
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
