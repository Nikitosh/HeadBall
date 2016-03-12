package com.nikitosh.headball.players;

import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.GameWorld;
import com.nikitosh.headball.Move;

public class EasyAIPlayer extends AIPlayer {

    private static final float HIT_RADIUS = 30f * Constants.WORLD_TO_BOX;
    private static final float JUMP_RADIUS = 45f * Constants.WORLD_TO_BOX;
    private static final float EPS = 3f * Constants.WORLD_TO_BOX;


    public EasyAIPlayer(GameWorld gameWorld, int footballerNumber) {
        super(gameWorld, footballerNumber);
    }

    @Override
    public Move getMove() {

        initialize();

        if (ballPositionY < myPositionY - footballers[footballerNumber].getRadius()) {
            move.setState(Constants.LEFT, true);
        } else {
            if (ballPositionX < myPositionX - footballers[footballerNumber].getRadius()) {
                if (myPositionX - ballPositionX < JUMP_RADIUS && myPositionY > ballPositionY) {
                    move.setState(Constants.JUMP, true);
                    move.setState(Constants.LEFT, true);
                } else {
                    move.setState(Constants.LEFT, true);
                }
            } else {
                if (ballPositionX - myPositionX < HIT_RADIUS) {
                    move.setState(Constants.HIT, true);
                }
                if (ballPositionX - myPositionX < JUMP_RADIUS + 2 * EPS
                        && ballPositionY - myPositionY < 3 * footballers[footballerNumber].getRadius()
                        && ballPositionY - myPositionY > footballers[footballerNumber].getRadius()) {
                    move.setState(Constants.JUMP, true);
                    move.setState(Constants.RIGHT, true);
                } else if (ballPositionX - myPositionX > JUMP_RADIUS + EPS) {
                    move.setState(Constants.RIGHT, true);
                } else if (Math.abs(ballPositionX - myPositionX) < JUMP_RADIUS
                        && ballPositionY - myPositionY > footballers[footballerNumber].getRadius()) {
                    move.setState(Constants.LEFT, true);
                } else {
                    move.setState(Constants.RIGHT, true);
                }
            }
        }
        makeMoveDependentOnPosition();
        return move;
    }
}

