package com.nikitosh.headball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.nikitosh.headball.actors.*;
import com.nikitosh.headball.ui.GameLabelStyle;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.GameSettings;

import java.util.ArrayList;

public class GameWorld {
    public static final float BOUNDS_WIDTH = 30;
    public static final int WALLS_COUNT = 4;

    private World box2dWorld;
    private Group group;

    private Footballer[] footballers;
    private Wall[] walls;
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
        field.setBounds(BOUNDS_WIDTH, BOUNDS_WIDTH, Constants.FIELD_WIDTH - 2 * BOUNDS_WIDTH, Constants.FIELD_HEIGHT - 2 * BOUNDS_WIDTH);
        group.addActor(field);

        footballers = new Footballer[2];
        initializeFootballers();

        walls = new Wall[WALLS_COUNT];
        walls[0] = new RectangleWall(box2dWorld, 0, 0, Constants.FIELD_WIDTH, BOUNDS_WIDTH);
        walls[1] = new RectangleWall(box2dWorld, 0, BOUNDS_WIDTH, BOUNDS_WIDTH, Constants.FIELD_HEIGHT - BOUNDS_WIDTH);
        walls[2] = new RectangleWall(box2dWorld, Constants.FIELD_WIDTH - BOUNDS_WIDTH, BOUNDS_WIDTH, BOUNDS_WIDTH, Constants.FIELD_HEIGHT - BOUNDS_WIDTH);
        walls[3] = new RectangleWall(box2dWorld, BOUNDS_WIDTH, Constants.FIELD_HEIGHT - BOUNDS_WIDTH, Constants.FIELD_WIDTH - 2 * BOUNDS_WIDTH, BOUNDS_WIDTH);
        groundWall = walls[0];

        for (int i = 0; i < WALLS_COUNT; i++)
            group.addActor(walls[i]);

        initializeBall();

        goals = new Goals[2];
        goals[0] = new Goals(box2dWorld, BOUNDS_WIDTH, BOUNDS_WIDTH + Constants.GOALS_HEIGHT, Constants.GOALS_WIDTH, Constants.CROSSBAR_HEIGHT, true);
        goals[1] = new Goals(box2dWorld, Constants.FIELD_WIDTH - BOUNDS_WIDTH - Constants.GOALS_WIDTH, BOUNDS_WIDTH + Constants.GOALS_HEIGHT, Constants.GOALS_WIDTH, Constants.CROSSBAR_HEIGHT, false);

        for (int i = 0; i < 2; i++) {
            group.addActor(goals[i]);
        }

        score = new int[2];
        score[0] = score[1] = 0;

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
        footballers[0].update(firstMove, ball);
        footballers[1].update(secondMove, ball);

        for (int i = 0; i < 2; i++) {
            if (goals[i].contains(ball.getPosition())) {
                score[1 - i]++;
                isGoal = true;
                startNewRound();
            }
        }

        box2dWorld.step(1 / 60f, 6, 2);
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
        for (int i = 0; i < 2; i++) {
            group.removeActor(footballers[i]);
        }
        for (int i = 0; i < 2; i++) {
            box2dWorld.destroyBody(footballers[i].getBody());
        }
        box2dWorld.destroyBody(ball.getBody());
    }

    private void initializeFootballers() {
        footballers[0] = new Footballer(box2dWorld, Constants.FIELD_WIDTH / 4, BOUNDS_WIDTH, true);
        footballers[1] = new Footballer(box2dWorld, 3 * Constants.FIELD_WIDTH / 4, BOUNDS_WIDTH, false);
        for (int i = 0; i < 2; i++) {
            group.addActor(footballers[i]);
            footballers[i].setZIndex(1);
        }
    }

    private void initializeBall() {
        ball = new Ball(box2dWorld, Constants.FIELD_WIDTH / 2, Constants.FIELD_HEIGHT / 2);
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
