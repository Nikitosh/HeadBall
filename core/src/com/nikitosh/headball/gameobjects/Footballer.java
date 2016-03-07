package com.nikitosh.headball.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.utils.Utilities;

public class Footballer {
    private static final float FOOTBALLER_SPEED = 100;
    private static final float FOOTBALLER_JUMP = 200;
    private static final float FOOTBALLER_DENSITY = 5f;
    private static final float FOOTBALLER_FRICTION = 0.1f;
    private static final float FOOTBALLER_RESTITUTION = 0f;

    private static final float ROTATOR_WIDTH = 10;
    private static final float ROTATOR_DENSITY = 5f;
    private static final float ROTATOR_FRICTION = 0f;
    private static final float ROTATOR_RESTITUTION = 0f;

    public static final float LEG_WIDTH = 16;
    public static final float LEG_HEIGHT = 10;
    private static final float LEG_DENSITY = 5f;
    private static final float LEG_FRICTION = 0f;
    private static final float LEG_RESTITUTION = 0f;

    private static final float JOINT_LOWER_ANGLE = 0;
    private static final float JOINT_UPPER_ANGLE = (float) Math.PI / 3;
    private static final float JOINT_ANGULAR_VELOCITY = 30f;
    private static final float JOINT_EPSILON = 0.1f;

    private Body body;
    private Body rotator;
    private Body leg;
    private RevoluteJoint revoluteJoint;

    private boolean inJump = false;
    private boolean isLeftSided; //is footballer left-sided or not
    private float radius;

    public Footballer(World world, float x, float y, boolean isLeftSided, float radius) {
        this.isLeftSided = isLeftSided;
        this.radius = radius;
        body = Utilities.getCircleBody(world,
                x * Constants.WORLD_TO_BOX, y * Constants.WORLD_TO_BOX, radius * Constants.WORLD_TO_BOX,
                FOOTBALLER_DENSITY, FOOTBALLER_FRICTION, FOOTBALLER_RESTITUTION,
                Constants.FOOTBALLER_CATEGORY, Constants.FOOTBALLER_MASK);
        body.setFixedRotation(true);
        body.getFixtureList().get(0).setUserData(this);

        /*
        Rotator is used for rotating leg around footballer's body
        This is done by creating two joints: revolute joint, connecting footballer's body and rotator, and
        weld joint, connecting rotator and leg
        */
        rotator = Utilities.getRectangularBody(world,
                (x - ROTATOR_WIDTH / 2) * Constants.WORLD_TO_BOX,
                (y - ROTATOR_WIDTH / 2) * Constants.WORLD_TO_BOX,
                ROTATOR_WIDTH * Constants.WORLD_TO_BOX, ROTATOR_WIDTH * Constants.WORLD_TO_BOX,
                ROTATOR_DENSITY, ROTATOR_FRICTION, ROTATOR_RESTITUTION,
                Constants.ROTATOR_CATEGORY, Constants.ROTATOR_MASK);

        leg = Utilities.getRectangularBody(world,
                (x - LEG_WIDTH / 2) * Constants.WORLD_TO_BOX,
                (y - radius - LEG_HEIGHT - 1) * Constants.WORLD_TO_BOX,
                LEG_WIDTH * Constants.WORLD_TO_BOX, LEG_HEIGHT * Constants.WORLD_TO_BOX,
                LEG_DENSITY, LEG_FRICTION, LEG_RESTITUTION,
                Constants.LEG_CATEGORY, Constants.LEG_MASK);

        WeldJointDef weldJointDef = new WeldJointDef();
        weldJointDef.initialize(rotator, leg, rotator.getWorldCenter());

        revoluteJoint = (RevoluteJoint) world.createJoint(getRevoluteJointDef());
        world.createJoint(weldJointDef);
    }

    public void update(Move move) {
        if (move == null) {
            return;
        }
        float x = ((move.getState(Constants.LEFT) ? -1 : 0) + (move.getState(Constants.RIGHT) ? 1 : 0));
        body.setLinearVelocity(x * FOOTBALLER_SPEED * Constants.WORLD_TO_BOX, body.getLinearVelocity().y);
        if (!inJump && move.getState(Constants.JUMP)) {
            inJump = true;
            body.setLinearVelocity(body.getLinearVelocity().x, FOOTBALLER_JUMP * Constants.WORLD_TO_BOX);
        }
        int turn = isLeftSided ? 1 : -1;
        if (move.getState(Constants.HIT)
                && revoluteJoint.getJointAngle() < revoluteJoint.getUpperLimit() - JOINT_EPSILON) {
            leg.setAngularVelocity(JOINT_ANGULAR_VELOCITY * turn);
        } else if (!move.getState(Constants.HIT)
                && revoluteJoint.getJointAngle() > revoluteJoint.getLowerLimit() + JOINT_EPSILON) {
            leg.setAngularVelocity(-JOINT_ANGULAR_VELOCITY * turn);
        } else {
            leg.setAngularVelocity(0);
        }
    }

    public void resetInJump() {
        this.inJump = false;
    }

    public Body getBody() {
        return body;
    }

    public Body getLeg() {
        return leg;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public float getRadius() {
        return radius * Constants.WORLD_TO_BOX;
    }

    public void setInitialPosition(World world, float positionX, float positionY) {
        world.destroyJoint(revoluteJoint);
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
        body.setTransform(positionX * Constants.WORLD_TO_BOX, positionY * Constants.WORLD_TO_BOX, 0);
        leg.setLinearVelocity(0, 0);
        leg.setAngularVelocity(0);
        leg.setTransform(positionX * Constants.WORLD_TO_BOX,
                (positionY - radius - 1 - LEG_HEIGHT / 2f) * Constants.WORLD_TO_BOX, 0);
        rotator.setLinearVelocity(0, 0);
        rotator.setAngularVelocity(0);
        rotator.setTransform(positionX * Constants.WORLD_TO_BOX, positionY * Constants.WORLD_TO_BOX, 0);
        revoluteJoint = (RevoluteJoint) world.createJoint(getRevoluteJointDef());
    }

    private RevoluteJointDef getRevoluteJointDef() {
        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.initialize(body, rotator, body.getWorldCenter());
        revoluteJointDef.enableLimit = true;
        if (!isLeftSided) {
            revoluteJointDef.lowerAngle = -JOINT_UPPER_ANGLE;
            revoluteJointDef.upperAngle = JOINT_LOWER_ANGLE;
        } else {
            revoluteJointDef.lowerAngle = JOINT_LOWER_ANGLE;
            revoluteJointDef.upperAngle = JOINT_UPPER_ANGLE;
        }
        return revoluteJointDef;
    }
}
