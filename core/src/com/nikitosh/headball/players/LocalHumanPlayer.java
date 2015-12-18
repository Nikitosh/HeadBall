package com.nikitosh.headball.players;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.ui.GameTextButtonTouchable;

public class LocalHumanPlayer extends Player {

    private Move move;

    public LocalHumanPlayer(GameTextButtonTouchable hitButton, GameTextButtonTouchable jumpButton, GameTextButtonTouchable leftButton, GameTextButtonTouchable rightButton) {
        move = new Move(false, false, false, false);
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

    }

    @Override
    public Move getMove() {
        return move;
    }
}
