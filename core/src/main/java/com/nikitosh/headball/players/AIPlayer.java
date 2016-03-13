package com.nikitosh.headball.players;

import com.nikitosh.headball.GameWorld;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.gameobjects.Ball;
import com.nikitosh.headball.gameobjects.Footballer;
import com.nikitosh.headball.utils.Constants;

public class AIPlayer implements Player {

    protected final GameWorld gameWorld;
    protected final int footballerNumber;

    protected Footballer[] footballers;

    protected float ballPositionX;
    protected float ballPositionY;
    protected float myPositionX;
    protected float myPositionY;

    protected Move move;


    public AIPlayer(GameWorld gameWorld, int footballerNumber) {
        this.gameWorld = gameWorld;
        this.footballerNumber = footballerNumber;
    }


    @Override
    public Move getMove() {
        return null;
    }

    protected void initialize() {
        footballers = gameWorld.getFootballers();
        Ball ball = gameWorld.getBall();

        ballPositionY = ball.getPosition().y;
        myPositionY = footballers[footballerNumber].getPosition().y;
        if (footballerNumber == 1) {
            ballPositionX = Constants.VIRTUAL_WIDTH * Constants.WORLD_TO_BOX - ball.getPosition().x;
            myPositionX = Constants.VIRTUAL_WIDTH * Constants.WORLD_TO_BOX
                    - footballers[footballerNumber].getPosition().x;
        } else {
            ballPositionX = ball.getPosition().x;
            myPositionX = footballers[footballerNumber].getPosition().x;
        }

        move = new Move();
    }

    protected void makeMoveDependentOnPosition() {
        if (footballerNumber == 1) {
            boolean tmp = move.getState(Constants.LEFT);
            move.setState(Constants.LEFT, move.getState(Constants.RIGHT));
            move.setState(Constants.RIGHT, tmp);
        }
    }
}
