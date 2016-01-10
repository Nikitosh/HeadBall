package com.nikitosh.headball.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.utils.Utilities;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

public class Footballer extends Actor {
    private static final float FOOTBALLER_RADIUS = 20;
    private static final float FOOTBALLER_SPEED = 100;
    private static final float FOOTBALLER_JUMP = 200;
    private static final float FOOTBALLER_DENSITY = 5f;
    private static final float FOOTBALLER_FRICTION = 0.1f;
    private static final float FOOTBALLER_RESTITUTION = 0f;

    private static final float ROTATOR_WIDTH = 10;
    private static final float ROTATOR_DENSITY = 5f;
    private static final float ROTATOR_FRICTION = 0f;
    private static final float ROTATOR_RESTITUTION = 0f;

    private static final float LEG_WIDTH = 16;
    private static final float LEG_HEIGHT = 10;
    private static final float LEG_DENSITY = 5f;
    private static final float LEG_FRICTION = 0f;
    private static final float LEG_RESTITUTION = 0f;

    private static final float JOINT_LOWER_ANGLE = 0;
    private static final float JOINT_UPPER_ANGLE = (float) Math.PI / 3;
    private static final float JOINT_ANGULAR_VELOCITY = 30f;
    private static final float JOINT_EPSILON = 0.1f;

    private Body body;
    private Body leg;
    private RevoluteJoint revoluteJoint;

    private boolean inJump = false;
    private boolean left;

    public Footballer(World world, float x, float y, boolean left) {
        this.left = left;

        body = Utilities.getCircleBody(world,
                x * Constants.WORLD_TO_BOX, y * Constants.WORLD_TO_BOX, FOOTBALLER_RADIUS * Constants.WORLD_TO_BOX,
                FOOTBALLER_DENSITY, FOOTBALLER_FRICTION, FOOTBALLER_RESTITUTION,
                Constants.FOOTBALLER_CATEGORY, Constants.FOOTBALLER_MASK);
        body.setFixedRotation(true);
        body.getFixtureList().get(0).setUserData(this);

        /*
        Rotator is used for rotating leg around footballer's body
        This is done by creating two joints: revolute joint, connecting footballer's body and rotator, and
        weld joint, connecting rotator and leg
        */
        Body rotator = Utilities.getRectangularBody(world,
                (x - ROTATOR_WIDTH / 2) * Constants.WORLD_TO_BOX, (y - ROTATOR_WIDTH / 2) * Constants.WORLD_TO_BOX,
                ROTATOR_WIDTH * Constants.WORLD_TO_BOX, ROTATOR_WIDTH * Constants.WORLD_TO_BOX,
                ROTATOR_DENSITY, ROTATOR_FRICTION, ROTATOR_RESTITUTION,
                Constants.ROTATOR_CATEGORY, Constants.ROTATOR_MASK);

        leg = Utilities.getRectangularBody(world,
                (x - LEG_WIDTH / 2) * Constants.WORLD_TO_BOX, (y - FOOTBALLER_RADIUS - LEG_HEIGHT - 1) * Constants.WORLD_TO_BOX,
                LEG_WIDTH * Constants.WORLD_TO_BOX, LEG_HEIGHT * Constants.WORLD_TO_BOX,
                LEG_DENSITY, LEG_FRICTION, LEG_RESTITUTION,
                Constants.LEG_CATEGORY, Constants.LEG_MASK);

        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.initialize(body, rotator, body.getWorldCenter());
        revoluteJointDef.enableLimit = true;
        revoluteJointDef.lowerAngle = JOINT_LOWER_ANGLE;
        revoluteJointDef.upperAngle = JOINT_UPPER_ANGLE;

        WeldJointDef weldJointDef = new WeldJointDef();
        weldJointDef.initialize(rotator, leg, rotator.getWorldCenter());

        revoluteJoint = (RevoluteJoint) world.createJoint(revoluteJointDef);
        world.createJoint(weldJointDef);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public float getRadius() {
        return FOOTBALLER_RADIUS * Constants.WORLD_TO_BOX;
    }

    public static float getFootballerRadius() {
        return FOOTBALLER_RADIUS * Constants.WORLD_TO_BOX;
    }

    public void update(Move move) {
        if (move == null) {
            return;
        }
        if (move.getState(Constants.LEFT) && !move.getState(Constants.RIGHT)) {
            body.setLinearVelocity(-FOOTBALLER_SPEED * Constants.WORLD_TO_BOX, body.getLinearVelocity().y);
        }
        if (move.getState(Constants.RIGHT) && !move.getState(Constants.LEFT)) {
            body.setLinearVelocity(FOOTBALLER_SPEED * Constants.WORLD_TO_BOX, body.getLinearVelocity().y);
        }
        if (!move.getState(Constants.LEFT) && !move.getState(Constants.RIGHT)) {
            body.setLinearVelocity(body.getLinearVelocity().x / 2, body.getLinearVelocity().y);
        }
        if (!inJump && move.getState(Constants.JUMP)) {
            inJump = true;
            body.setLinearVelocity(body.getLinearVelocity().x, FOOTBALLER_JUMP * Constants.WORLD_TO_BOX);
        }
        if (move.getState(Constants.HIT) &&
                revoluteJoint.getJointAngle() < revoluteJoint.getUpperLimit() - JOINT_EPSILON) {
            leg.setAngularVelocity(JOINT_ANGULAR_VELOCITY);
        }
        else if (!move.getState(Constants.HIT) &&
                revoluteJoint.getJointAngle() > revoluteJoint.getLowerLimit() + JOINT_EPSILON) {
            leg.setAngularVelocity(-JOINT_ANGULAR_VELOCITY);
        }
        else {
            leg.setAngularVelocity(0);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Box2DSprite box2DSprite;
        if (left) {
            box2DSprite = new Box2DSprite(AssetLoader.footballerTexture);
        }
        else {
            box2DSprite = new Box2DSprite(AssetLoader.reversedFootballerTexture);
        }
        box2DSprite.draw(batch,
                body.getPosition().x * Constants.BOX_TO_WORLD, body.getPosition().y * Constants.BOX_TO_WORLD,
                2 * FOOTBALLER_RADIUS, 2 * FOOTBALLER_RADIUS, body.getAngle());
        box2DSprite = new Box2DSprite(AssetLoader.footballerTexture);
        box2DSprite.draw(batch,
                leg.getPosition().x * Constants.BOX_TO_WORLD,
                leg.getPosition().y * Constants.BOX_TO_WORLD,
                LEG_WIDTH, LEG_HEIGHT, leg.getAngle());
    }

    public void setInJump(boolean inJump) {
        this.inJump = inJump;
    }

    public Body getBody() {
        return body;
    }
}
