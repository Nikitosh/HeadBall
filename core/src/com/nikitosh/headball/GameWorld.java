package com.nikitosh.headball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GameWorld {
    public static final float BOUNDS_WIDTH = 30;

    private World box2dWorld;
    private Group group;

    private Footballer[] footballers;
    private Wall[] walls;
    private Wall groundWall;
    private Ball ball;
    private Goals[] goals;
    private int[] score;
    private Label scoreLabel;

    public GameWorld() {
        box2dWorld = new World(new Vector2(0f, -100f * Constants.WORLD_TO_BOX), true);
        group = new Group();
        group.setBounds(0, Constants.BUTTONS_HEIGHT, Constants.FIELD_WIDTH, Constants.FIELD_HEIGHT);

        Image field = new Image(AssetLoader.fieldTexture);
        field.setBounds(BOUNDS_WIDTH, BOUNDS_WIDTH, Constants.FIELD_WIDTH - 2 * BOUNDS_WIDTH, Constants.FIELD_HEIGHT - 2 * BOUNDS_WIDTH);
        group.addActor(field);

        footballers = new Footballer[2];
        initializeFootballers();

        walls = new Wall[10];
        walls[0] = new RectangleWall(box2dWorld, 0, 0, Constants.FIELD_WIDTH, BOUNDS_WIDTH);
        walls[1] = new RectangleWall(box2dWorld, 0, BOUNDS_WIDTH, BOUNDS_WIDTH, Constants.FIELD_HEIGHT - BOUNDS_WIDTH);
        walls[2] = new RectangleWall(box2dWorld, Constants.FIELD_WIDTH - BOUNDS_WIDTH, BOUNDS_WIDTH, BOUNDS_WIDTH, Constants.FIELD_HEIGHT - BOUNDS_WIDTH);
        walls[3] = new RectangleWall(box2dWorld, BOUNDS_WIDTH, Constants.FIELD_HEIGHT - BOUNDS_WIDTH, Constants.FIELD_WIDTH - 2 * BOUNDS_WIDTH, BOUNDS_WIDTH);
        groundWall = walls[0];

        for (int i = 0; i < 4; i++)
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

        scoreLabel = new Label("", new GameLabelStyle());
        scoreLabel.setPosition(Constants.FIELD_WIDTH / 2 - scoreLabel.getWidth() / 2, 2 * Constants.FIELD_HEIGHT / 3 - scoreLabel.getHeight() / 2);
        group.addActor(scoreLabel);

        box2dWorld.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Object contactA = contact.getFixtureA().getUserData();
                Object contactB = contact.getFixtureB().getUserData();
                Gdx.app.log("ContactListener", contact.getFixtureA().toString());
                Gdx.app.log("ContactListener", contact.getFixtureB().toString());
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
        footballers[0].update(firstMove);
        footballers[1].update(secondMove);

        for (int i = 0; i < 2; i++) {
            if (goals[i].contains(ball.getPosition())) {
                score[1 - i]++;
                if (GameSettings.getBoolean("sound")) {
                    AssetLoader.goalSound.play();
                }
                startNewRound();
            }
        }
        scoreLabel.setText(Integer.toString(score[0]) + " : " + Integer.toString(score[1]));
        scoreLabel.setPosition(Constants.FIELD_WIDTH / 2 - scoreLabel.getWidth() / 2, 2 * Constants.FIELD_HEIGHT / 3 - scoreLabel.getHeight() / 2);

        box2dWorld.step(1f / 60f, 6, 2);
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
}
