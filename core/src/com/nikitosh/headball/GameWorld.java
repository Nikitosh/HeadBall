package com.nikitosh.headball;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.actors.*;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class GameWorld {
    private static final float GRAVITY_X = 0;
    private static final float GRAVITY_Y = -300f * Constants.WORLD_TO_BOX;
    private static final boolean TO_SLEEP = true; //improve performance for box2D World
    private static final float BOX2D_DELTA_TIME = 1 / 60f;
    private static final int BOX2D_VELOCITY_ITERATIONS = 6;
    private static final int BOX2D_POSITION_ITERATIONS = 2;

    private World box2dWorld;
    private Group group;

    private Array<Float> initialFootballerPositionX;
    private Array<Float> initialFootballerPositionY;
    private float initialBallPositionX;
    private float initialBallPositionY;

    private Image field;
    private float height;
    private Footballer[] footballers;
    private Array<Wall> walls;
    private Ball ball;
    private Goals[] goals;
    private int[] score;
    private boolean isGoal = false;
    private boolean isEnded = false;
    private boolean isDrawResultPossible = true;
    private float gameDuration = 0;
    private float accumulator = 0;

    public GameWorld() {
        box2dWorld = new World(new Vector2(GRAVITY_X, GRAVITY_Y), TO_SLEEP);
        group = new Group();

        field = new Image(AssetLoader.getFieldTexture());
        group.addActor(field);

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
                    footballer.setInJump(false);
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
        if (gameDuration > Constants.GAME_DURATION && (isDrawResultPossible || score[0] != score[1])) {
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

    public void startNewRound() {
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
            group.addActor(footballers[i]);
        }
    }

    public void createBall(float initialPositionX, float initialPositionY, float initialRadius) {
        initialBallPositionX = initialPositionX;
        initialBallPositionY = initialPositionY;
        ball = new Ball(box2dWorld, initialPositionX, initialPositionY, initialRadius);
        group.addActor(ball);
    }

    public void createGoals(Array<Float> initialPositionX, Array<Float> initialPositionY,
                            float goalsWidth, float goalsHeight, float crossbarHeight,
                            Array<Boolean> initialLeft) {
        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            goals[i] = new Goals(box2dWorld,
                    initialPositionX.get(i), initialPositionY.get(i),
                    goalsWidth, goalsHeight, crossbarHeight, initialLeft.get(i));
            group.addActor(goals[i]);
        }
    }

    public void createWalls(Array<Float> initialPositionX, Array<Float> initialPositionY,
                             Array<Float> initialWidth, Array<Float> initialHeight) {
        for (int i = 0; i < initialPositionX.size; i++) {
            walls.add(new RectangleWall(box2dWorld, initialPositionX.get(i), initialPositionY.get(i),
                    initialWidth.get(i), initialHeight.get(i)));
            group.addActor(walls.get(i));
        }
    }

    public World getBox2dWorld() {
        return box2dWorld;
    }

    public Group getGroup() {
        return group;
    }

    public Footballer[] getFootballers() {
        return footballers;
    }

    public Ball getBall() {
        return ball;
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

    public void setSize(float width, float height) {
        this.height = height;
        field.setBounds(0, 0, width, height);
    }

    public float getHeight() {
        return height;
    }
}
