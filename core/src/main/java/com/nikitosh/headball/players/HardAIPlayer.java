package com.nikitosh.headball.players;

import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.GameWorld;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.gameobjects.Footballer;

public class HardAIPlayer extends AIPlayer {

    private static final float HIT_RADIUS = 40f * Constants.WORLD_TO_BOX;
    private static final float JUMP_RADIUS = 60f * Constants.WORLD_TO_BOX;
    private static final float DEFENCE_POSITION = 80f * Constants.WORLD_TO_BOX;
    private static final float VISION_RADIUS = 120f * Constants.WORLD_TO_BOX;
    private static final float EPS = 3f * Constants.WORLD_TO_BOX;
    private static final float DEFENCE_ZONE = (Constants.VIRTUAL_WIDTH / 4f) * Constants.WORLD_TO_BOX;


    public HardAIPlayer(GameWorld gameWorld, int footballerNumber) {
        super(gameWorld, footballerNumber);
    }

    @Override
    public Move getMove() {

        initialize();

        if (gameWorld.getScore()[footballerNumber] >= gameWorld.getScore()[1 - footballerNumber]) {
            move = defenseMod(ballPositionX, ballPositionY, myPositionX, myPositionY);
        } else {
            move = attackMod(ballPositionX, ballPositionY, myPositionX, myPositionY);
        }

        makeMoveDependentOnPosition();
        return move;
    }

    private Move defenseMod(float ballPositionX, float ballPositionY, float myPositionX, float myPositionY) {
        Footballer[] footballers = gameWorld.getFootballers();
        Move move = new Move();
        if (ballPositionX - myPositionX > VISION_RADIUS
                && (ballPositionX > (Constants.VIRTUAL_WIDTH * Constants.WORLD_TO_BOX) / 2f - EPS)
                && myPositionX < ballPositionX) {
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
                if (ballPositionX - myPositionX > VISION_RADIUS
                        && ballPositionY - myPositionY > footballers[footballerNumber].getRadius() * 4f
                        && myPositionX > DEFENCE_POSITION - EPS
                        && myPositionX < DEFENCE_ZONE) {
                    move.setState(Constants.LEFT, true);
                } else {
                    move = attackMod(ballPositionX, ballPositionY, myPositionX, myPositionY);
                }
            }
        }
        return move;
    }

    private Move attackMod(float ballPositionX, float ballPositionY, float myPositionX, float myPositionY) {
        Footballer[] footballers = gameWorld.getFootballers();
        Move move = new Move();
        if (ballPositionY < myPositionY - footballers[footballerNumber].getRadius()) {
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
                if (ballPositionX - myPositionX < HIT_RADIUS) {
                    move.setState(Constants.HIT, true);
                }
                if (myPositionX > footballers[1 - footballerNumber].getPosition().x
                        && ballPositionY < 3f * footballers[footballerNumber].getRadius()) {
                    move.setState(Constants.RIGHT, true);
                } else if (ballPositionX < myPositionX && ballPositionY > myPositionY) {
                    move.setState(Constants.LEFT, true);
                    move.setState(Constants.JUMP, true);
                } else if (ballPositionX - myPositionX > VISION_RADIUS
                        && ballPositionY - myPositionY > footballers[footballerNumber].getRadius() * 4f) {
                    move.setState(Constants.RIGHT, true);
                } else if (ballPositionX - myPositionX > footballers[footballerNumber].getRadius() / 2f
                        && ballPositionX - myPositionX < VISION_RADIUS
                        && ballPositionY < myPositionY  + footballers[footballerNumber].getRadius() * 2f
                        && ballPositionY > myPositionY + footballers[footballerNumber].getRadius()) {
                    move.setState(Constants.JUMP, true);
                    move.setState(Constants.RIGHT, true);
                } else if ((ballPositionX - myPositionX < footballers[footballerNumber].getRadius() / 2f
                        && ballPositionY > myPositionY)
                        || (ballPositionX - myPositionX < VISION_RADIUS
                        && ballPositionY > myPositionY + footballers[footballerNumber].getRadius() * 2f
                        && myPositionX > DEFENCE_POSITION && myPositionX < DEFENCE_ZONE)) {
                    move.setState(Constants.JUMP, true);
                    move.setState(Constants.LEFT, true);
                } else {
                    move.setState(Constants.RIGHT, true);
                }
            }
        }
        return move;
    }
}
