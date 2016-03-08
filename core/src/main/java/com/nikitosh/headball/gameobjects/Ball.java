package com.nikitosh.headball.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.Utilities;

public class Ball extends GameObject{
    private static final float BALL_DENSITY = 1f;
    private static final float BALL_FRICTION = 0.5f;
    private static final float BALL_RESTITUTION = 0.5f;

    public Ball(World world, float x, float y, float radius) {
        this.radius = radius;
        body = Utilities.getCircleBody(world,
                x * Constants.WORLD_TO_BOX, y * Constants.WORLD_TO_BOX, radius * Constants.WORLD_TO_BOX,
                BALL_DENSITY, BALL_FRICTION, BALL_RESTITUTION,
                Constants.GAME_OBJECT_CATEGORY, Constants.GAME_OBJECT_MASK);
        body.setAngularDamping(2f);
        body.setSleepingAllowed(false);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public float getRadius() {
        return radius;
    }

    public float getAngle() {
        return body.getAngle();
    }

    public float getVelocity() {
        return body.getLinearVelocity().len();
    }

    public void applyLinearImpulse(Vector2 impulse) {
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
    }

    public void setInitialPosition(float positionX, float positionY) {
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
        body.setTransform(positionX * Constants.WORLD_TO_BOX, positionY * Constants.WORLD_TO_BOX, 0);
    }
}
