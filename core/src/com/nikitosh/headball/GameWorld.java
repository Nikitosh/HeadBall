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
    private static final float BOUNDS_WIDTH = 30;
    private static final float[] FOOTBALLER_INITIAL_POSITION_X = {
            Constants.FIELD_WIDTH / 4,
            3 * Constants.FIELD_WIDTH / 4
    };
    private static final float[] FOOTBALLER_INITIAL_POSITION_Y = {BOUNDS_WIDTH, BOUNDS_WIDTH};
    private static final boolean[] FOOTBALLER_INITIAL_LEFT = {true, false};
    private static final float BALL_INITIAL_POSITION_X = Constants.FIELD_WIDTH / 2;
    private static final float BALL_INITIAL_POSITION_Y = Constants.FIELD_HEIGHT / 2;
    private static final float[] GOALS_INITIAL_POSITION_X = {
            BOUNDS_WIDTH,
            Constants.FIELD_WIDTH - BOUNDS_WIDTH - Constants.GOALS_WIDTH
    };
    private static final float[] GOALS_INITIAL_POSITION_Y = {
            BOUNDS_WIDTH + Constants.GOALS_HEIGHT,
            BOUNDS_WIDTH + Constants.GOALS_HEIGHT
    };
    private static final boolean[] GOALS_INITIAL_LEFT = {true, false};

    private World box2dWorld;
    private Group group;

    private Footballer[] footballers;
    private Array<Wall> walls;
    private Wall groundWall;
    private Ball ball;
    private Goals[] goals;
    private int[] score;
    private boolean isGoal = false;
    private boolean isEnded = false;
    private float gameDuration = 0;

    public GameWorld() {
        box2dWorld = new World(new Vector2(0f, -300f * Constants.WORLD_TO_BOX), true);
        group = new Group();

        Image field = new Image(AssetLoader.fieldTexture);
        field.setBounds(BOUNDS_WIDTH, BOUNDS_WIDTH,
                Constants.FIELD_WIDTH - 2 * BOUNDS_WIDTH, Constants.FIELD_HEIGHT - 2 * BOUNDS_WIDTH);
        group.addActor(field);

        footballers = new Footballer[Constants.PLAYERS_NUMBER];
        initializeFootballers();

        walls = new Array<Wall>();
        walls.add(new GroundWall(box2dWorld,
                0, 0,
                Constants.FIELD_WIDTH, BOUNDS_WIDTH));
        walls.add(new RectangleWall(box2dWorld,
                0, BOUNDS_WIDTH,
                BOUNDS_WIDTH, Constants.FIELD_HEIGHT - BOUNDS_WIDTH));
        walls.add(new RectangleWall(box2dWorld,
                Constants.FIELD_WIDTH - BOUNDS_WIDTH, BOUNDS_WIDTH,
                BOUNDS_WIDTH, Constants.FIELD_HEIGHT - BOUNDS_WIDTH));
        walls.add(new RectangleWall(box2dWorld,
                BOUNDS_WIDTH, Constants.FIELD_HEIGHT - BOUNDS_WIDTH,
                Constants.FIELD_WIDTH - 2 * BOUNDS_WIDTH, BOUNDS_WIDTH));
        groundWall = walls.get(0);

        for (int i = 0; i < walls.size; i++)
            group.addActor(walls.get(i));

        initializeBall();

        goals = new Goals[Constants.PLAYERS_NUMBER];
        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            goals[i] = new Goals(box2dWorld,
                    GOALS_INITIAL_POSITION_X[i], GOALS_INITIAL_POSITION_Y[i],
                    Constants.GOALS_WIDTH, Constants.CROSSBAR_HEIGHT, GOALS_INITIAL_LEFT[i]);
            group.addActor(goals[i]);
        }

        score = new int[Constants.PLAYERS_NUMBER];
        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            score[i] = 0;
        }

        box2dWorld.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Object contactA = contact.getFixtureA().getUserData();
                Object contactB = contact.getFixtureB().getUserData();
                if (contactA == null || contactB == null) {
                    return;
                }
                if (contactA instanceof Footballer && contactB.equals(groundWall)) {
                    ((Footballer) contactA).setInJump(false);
                }
                if (contactB instanceof Footballer && contactA.equals(groundWall)) {
                    ((Footballer) contactB).setInJump(false);
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
        box2dWorld.step(1 / 60f, 6, 2);
        footballers[0].update(firstMove);
        footballers[1].update(secondMove);

        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            if (goals[i].contains(ball.getPosition())) {
                score[1 - i]++;
                isGoal = true;
                startNewRound();
            }
        }

        gameDuration += delta;

        if (gameDuration > Constants.GAME_DURATION) {
            isEnded = true;
        }

    }

    public boolean isGoal() {
        return isGoal;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void startNewRound() {
        destroy();
        initializeFootballers();
        initializeBall();
    }

    private void destroy() {
        group.removeActor(ball);
        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            group.removeActor(footballers[i]);
        }
        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            box2dWorld.destroyBody(footballers[i].getBody());
        }
        box2dWorld.destroyBody(ball.getBody());
    }

    private void initializeFootballers() {
        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            footballers[i] = new Footballer(box2dWorld,
                    FOOTBALLER_INITIAL_POSITION_X[i], FOOTBALLER_INITIAL_POSITION_Y[i], FOOTBALLER_INITIAL_LEFT[i]);
            group.addActor(footballers[i]);
            footballers[i].setZIndex(1);
        }
    }

    private void initializeBall() {
        ball = new Ball(box2dWorld, BALL_INITIAL_POSITION_X, BALL_INITIAL_POSITION_Y);
        group.addActor(ball);
        ball.setZIndex(1);
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
}
