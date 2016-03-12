package com.nikitosh.headball;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.gameobjects.Ball;
import com.nikitosh.headball.gameobjects.Footballer;
import com.nikitosh.headball.gameobjects.Goals;
import com.nikitosh.headball.gameobjects.RectangleWall;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.GameSettings;

public class GameWorld {
    private static final float GRAVITY_X = 0;
    private static final float GRAVITY_Y = -300f * Constants.WORLD_TO_BOX;
    private static final boolean TO_SLEEP = true; //improve performance for box2D World
    private static final float BOX2D_DELTA_TIME = 1 / 60f;
    private static final int BOX2D_VELOCITY_ITERATIONS = 6;
    private static final int BOX2D_POSITION_ITERATIONS = 2;

    private final World box2dWorld;

    private Array<Float> initialFootballerPositionX;
    private Array<Float> initialFootballerPositionY;
    private float initialBallPositionX;
    private float initialBallPositionY;

    private final Footballer[] footballers;
    private final Array<RectangleWall> walls;
    private Ball ball;
    private final Goals[] goals;
    private final int[] score;
    private boolean isGoal = false;
    private boolean isEnded = false;
    private boolean isDrawResultPossible = true;
    private float gameDuration = 0;
    private float accumulator = 0;

    public GameWorld() {
        box2dWorld = new World(new Vector2(GRAVITY_X, GRAVITY_Y), TO_SLEEP);

        walls = new Array<>();
        footballers = new Footballer[Constants.PLAYERS_NUMBER];
        goals = new Goals[Constants.PLAYERS_NUMBER];

        score = new int[Constants.PLAYERS_NUMBER];
        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            score[i] = 0;
        }

        box2dWorld.setContactListener(new ContactListener() {
            private void handleFootballerWallCollision(Footballer footballer, RectangleWall wall) {
                if (footballer.getBody().getPosition().y
                        >= wall.getBody().getPosition().y + footballer.getRadius()) {
                    footballer.resetInJump();
                }
            }

            @Override
            public void beginContact(Contact contact) {
                Object contactA = contact.getFixtureA().getUserData();
                Object contactB = contact.getFixtureB().getUserData();
                if (contactA == null || contactB == null) {
                    return;
                }
                if (contactA instanceof Footballer && contactB instanceof RectangleWall) {
                    handleFootballerWallCollision((Footballer) contactA, (RectangleWall) contactB);
                }
                if (contactB instanceof Footballer && contactA instanceof RectangleWall) {
                    handleFootballerWallCollision((Footballer) contactB, (RectangleWall) contactA);
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    public void update(float delta, Move firstMove, Move secondMove) {
        isGoal = false;
        gameDuration += delta;
        if (gameDuration > GameSettings.getInteger(Constants.GAME_DURATION)
                && (isDrawResultPossible || score[0] != score[1])) {
            isEnded = true;
        }

        accumulator += delta;
        while (accumulator >= BOX2D_DELTA_TIME) {
            box2dWorld.step(BOX2D_DELTA_TIME, BOX2D_VELOCITY_ITERATIONS, BOX2D_POSITION_ITERATIONS);
            accumulator -= BOX2D_DELTA_TIME;
        }
        footballers[0].update(firstMove);
        footballers[1].update(secondMove);

        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            if (ball.getVelocity() == 0 && goals[i].containsOnUpperEdge(ball.getPosition(), ball.getRadius())) {
                ball.applyLinearImpulse(goals[i].getToFieldCenterVector().scl(Constants.WORLD_TO_BOX));
            }
        }

        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            if (goals[i].contains(ball.getPosition())) {
                score[1 - i]++;
                isGoal = true;
                startNewRound();
            }
        }
    }

    public boolean isGoal() {
        return isGoal;
    }

    public boolean isEnded() {
        return isEnded;
    }

    private void startNewRound() {
        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            footballers[i].setInitialPosition(box2dWorld,
                    initialFootballerPositionX.get(i), initialFootballerPositionY.get(i));
        }
        ball.setInitialPosition(initialBallPositionX, initialBallPositionY);
    }

    public void restartGame() {
        startNewRound();
        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            score[i] = 0;
        }
        gameDuration = 0;
    }

    public void createFootballers(Array<Float> initialPositionX, Array<Float> initialPositionY,
                                  Array<Boolean> initialLeft, float initialRadius) {
        initialFootballerPositionX = initialPositionX;
        initialFootballerPositionY = initialPositionY;
        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            footballers[i] = new Footballer(box2dWorld,
                    initialPositionX.get(i), initialPositionY.get(i), initialLeft.get(i), initialRadius);
        }
    }

    public void createBall(float initialPositionX, float initialPositionY, float initialRadius) {
        initialBallPositionX = initialPositionX;
        initialBallPositionY = initialPositionY;
        ball = new Ball(box2dWorld, initialPositionX, initialPositionY, initialRadius);
    }

    public void createGoals(Array<Float> initialPositionX, Array<Float> initialPositionY,
                            float goalsWidth, float goalsHeight, float crossbarHeight,
                            Array<Boolean> initialLeft) {
        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            goals[i] = new Goals(box2dWorld,
                    initialPositionX.get(i), initialPositionY.get(i),
                    goalsWidth, goalsHeight, crossbarHeight, initialLeft.get(i));
        }
    }

    public void createWalls(Array<Array<Float>> walls) {
        for (Array<Float> wall : walls) {
            RectangleWall newWall = new RectangleWall(box2dWorld, wall.get(0), wall.get(1),
                    wall.get(2) - wall.get(0), wall.get(3) - wall.get(1), wall.get(4) != 0);
            this.walls.add(newWall);
        }
    }

    public World getBox2dWorld() {
        return box2dWorld;
    }

    public Ball getBall() {
        return ball;
    }

    public Footballer[] getFootballers() {
        return footballers;
    }

    public Goals[] getGoals() {
        return goals;
    }

    public Array<RectangleWall> getWalls() {
        return walls;
    }

    public int[] getScore() {
        return score;
    }

    public float getGameDuration() {
        return gameDuration;
    }

    public void setDrawResultPossible(boolean isDrawResultPossible) {
        this.isDrawResultPossible = isDrawResultPossible;
    }
}
