package com.nikitosh.headball.players;

import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.GameWorld;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.actors.Ball;
import com.nikitosh.headball.actors.Footballer;

public class AIPlayer implements Player {
    private GameWorld gameWorld;
    private int footballerNumber;

    private static final float HIT_RADIUS = 30f * Constants.WORLD_TO_BOX;
    private static final float JUMP_RADIUS = 45f * Constants.WORLD_TO_BOX;


    public AIPlayer(GameWorld gameWorld, int footballerNumber) {
        this.gameWorld = gameWorld;
        this.footballerNumber = footballerNumber;
    }

    @Override
    public Move getMove() {

        Footballer[] footballers = gameWorld.getFootballers();
        Ball ball = gameWorld.getBall();

        float ballPositionX;
        float ballPositionY = ball.getPosition().y;
        float myPositionX;
        float myPositionY = footballers[footballerNumber].getPosition().y;

        Move move = new Move();
        if (footballerNumber == 1) {
            ballPositionX = Constants.VIRTUAL_WIDTH * Constants.WORLD_TO_BOX - ball.getPosition().x;
            myPositionX = Constants.VIRTUAL_WIDTH * Constants.WORLD_TO_BOX - footballers[footballerNumber].getPosition().x;
        }
        else {
            ballPositionX = ball.getPosition().x;
            myPositionX = footballers[footballerNumber].getPosition().x;
        }

        if (ballPositionY < myPositionY - footballers[footballerNumber].getRadius()) {
            move.setState(Constants.LEFT, true);
        }
        else {
            if (ballPositionX < myPositionX - footballers[footballerNumber].getRadius()) {
                if (myPositionX - ballPositionX < JUMP_RADIUS && myPositionY > ballPositionY) {
                    move.setState(Constants.JUMP, true);
                    move.setState(Constants.LEFT, true);
                }
                else {
                    move.setState(Constants.LEFT, true);
                }
            }
            else {
                if (ballPositionX - myPositionX > footballers[footballerNumber].getRadius() / 4 && ballPositionX - myPositionX < HIT_RADIUS) {
                    move.setState(Constants.HIT, true);
                    move.setState(Constants.RIGHT, true);
                }
                else if (Math.abs(ballPositionX - myPositionX) < JUMP_RADIUS && ballPositionY > myPositionY) {
                    move.setState(Constants.JUMP, true);
                    move.setState(Constants.LEFT, true);
                }
                else {
                    move.setState(Constants.RIGHT, true);
                }
            }
        }
        if (footballerNumber == 1) {
            boolean tmp = move.getState(Constants.LEFT);
            move.setState(Constants.LEFT, move.getState(Constants.RIGHT));
            move.setState(Constants.RIGHT, tmp);
        }
        return move;
    }
}