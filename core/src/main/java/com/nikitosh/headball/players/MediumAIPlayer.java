package com.nikitosh.headball.players;

import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.GameWorld;
import com.nikitosh.headball.Move;

public class MediumAIPlayer extends AIPlayer {

    private static final float HIT_RADIUS = 40f * Constants.WORLD_TO_BOX;
    private static final float JUMP_RADIUS = 55f * Constants.WORLD_TO_BOX;
    private static final float DEFENCE_POSITION = (Constants.VIRTUAL_WIDTH / 8f) * Constants.WORLD_TO_BOX;
    private static final float VISION_RADIUS = (Constants.VIRTUAL_WIDTH / 8f) * Constants.WORLD_TO_BOX;
    private static final float EPS = 3f * Constants.WORLD_TO_BOX;
    private static final float DEFENCE_ZONE = (Constants.VIRTUAL_WIDTH / 4f) * Constants.WORLD_TO_BOX;

    public MediumAIPlayer(GameWorld gameWorld, int footballerNumber) {
        super(gameWorld, footballerNumber);
    }

    @Override
    public Move getMove() {

        initialize();

        if ((ballPositionX - myPositionX > VISION_RADIUS
                && ballPositionX > (Constants.VIRTUAL_WIDTH * Constants.WORLD_TO_BOX) / 2  - EPS)
                && myPositionX < Constants.VIRTUAL_WIDTH * Constants.WORLD_TO_BOX / 2) {
            if (myPositionX > DEFENCE_POSITION) {
                move.setState(Constants.LEFT, true);
            } else if (myPositionX < DEFENCE_POSITION - EPS) {
                move.setState(Constants.RIGHT, true);
            }
        } else if (ballPositionY < myPositionY - footballers[footballerNumber].getRadius()) {
            move.setState(Constants.LEFT, true);
        } else {
            if (ballPositionX < myPositionX - footballers[footballerNumber].getRadius()) {
                if (myPositionX - ballPositionX < JUMP_RADIUS / 2f && myPositionY > ballPositionY) {
                    move.setState(Constants.JUMP, true);
                    move.setState(Constants.LEFT, true);
                } else {
                    move.setState(Constants.LEFT, true);
                }
            } else {
                if (ballPositionX - myPositionX > footballers[footballerNumber].getRadius() / 4
                        && ballPositionX - myPositionX < HIT_RADIUS) {
                    move.setState(Constants.HIT, true);
                    move.setState(Constants.RIGHT, true);
                } else if ((Math.abs(ballPositionX - myPositionX) < JUMP_RADIUS / 3
                        && ballPositionY > myPositionY)
                        || (Math.abs(ballPositionX - myPositionX) < VISION_RADIUS
                        && ballPositionY > myPositionY + footballers[footballerNumber].getRadius() * 2
                        && myPositionX > DEFENCE_POSITION && myPositionX < DEFENCE_ZONE)) {
                    move.setState(Constants.JUMP, true);
                    move.setState(Constants.LEFT, true);
                } else if (Math.abs(ballPositionX - myPositionX) < VISION_RADIUS && ballPositionY > myPositionY) {
                    move.setState(Constants.JUMP, true);
                    move.setState(Constants.RIGHT, true);
                } else {
                    move.setState(Constants.RIGHT, true);
                }
            }
        }

        makeMoveDependentOnPosition();
        return move;
    }
}
