package com.nikitosh.headball.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
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

import static net.dermetfan.gdx.physics.box2d.Box2DUtils.height;
import static net.dermetfan.gdx.physics.box2d.Box2DUtils.position;
import static net.dermetfan.gdx.physics.box2d.Box2DUtils.width;

public class Footballer extends Actor {
    private static final float FOOTBALLER_RADIUS = 20;
    private static final float FOOTBALLER_SPEED = 100;
    private static final float FOOTBALLER_JUMP = 200;
    private static final float FOOTBALLER_DENSITY = 45f;
    private static final float FOOTBALLER_FRICION = 0.1f;
    private static final float FOOTBALLER_RESTITUTION = 0f;

    private Body body;
    private Body leg;
    private RevoluteJoint revoluteJoint;

    private boolean inJump = false;
    private boolean left;

    public Footballer(World world, float x, float y, boolean left) {
        this.left = left;

        body = Utilities.getCircleBody(world, x * Constants.WORLD_TO_BOX, y * Constants.WORLD_TO_BOX,
                FOOTBALLER_RADIUS * Constants.WORLD_TO_BOX, FOOTBALLER_DENSITY, FOOTBALLER_FRICION, FOOTBALLER_RESTITUTION);
        body.setFixedRotation(true);
        body.getFixtureList().get(0).setUserData(this);
        Filter filter = new Filter();
        filter.maskBits = 1;
        filter.categoryBits = 1;
        body.getFixtureList().get(0).setFilterData(filter);

        Body rotator = Utilities.getRectangularBody(world, (x - 2) * Constants.WORLD_TO_BOX, (y - 2) * Constants.WORLD_TO_BOX,
                4 * Constants.WORLD_TO_BOX, 4 * Constants.WORLD_TO_BOX, 45, 0, 0);
        filter = new Filter();
        filter.categoryBits = 2;
        rotator.getFixtureList().get(0).setFilterData(filter);

        leg = Utilities.getRectangularBody(world, (x + FOOTBALLER_RADIUS + 1) * Constants.WORLD_TO_BOX, (y - 3) * Constants.WORLD_TO_BOX,
                10 * Constants.WORLD_TO_BOX, 6 * Constants.WORLD_TO_BOX, 150, 0, 0);
        filter = new Filter();
        filter.categoryBits = 2;
        leg.getFixtureList().get(0).setFilterData(filter);


        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.initialize(body, rotator, body.getWorldCenter());
        revoluteJointDef.enableLimit = true;
        revoluteJointDef.lowerAngle = -(float) Math.PI / 2;
        revoluteJointDef.upperAngle = 0;

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
        if (move.isLeft() && !move.isRight()) {
            body.setLinearVelocity(-FOOTBALLER_SPEED * Constants.WORLD_TO_BOX, body.getLinearVelocity().y);
        }
        if (move.isRight() && !move.isLeft()) {
            body.setLinearVelocity(FOOTBALLER_SPEED * Constants.WORLD_TO_BOX, body.getLinearVelocity().y);
        }
        if (!move.isLeft() && !move.isRight()) {
            body.setLinearVelocity(body.getLinearVelocity().x / 2, body.getLinearVelocity().y);
        }
        if (inJump == false && move.isJump()) {
            inJump = true;
            body.setLinearVelocity(body.getLinearVelocity().x, FOOTBALLER_JUMP * Constants.WORLD_TO_BOX);
        }
        if (move.isHit()) {
            leg.setAngularVelocity(30f);
        }
        else {
            leg.setAngularVelocity(-30f);
        }
        if (revoluteJoint.getJointAngle() < revoluteJoint.getLowerLimit()) {
            leg.setAngularVelocity(30f);
        }
        if (revoluteJoint.getJointAngle() > revoluteJoint.getUpperLimit()) {
            leg.setAngularVelocity(-30f);
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
                body.getPosition().x * Constants.BOX_TO_WORLD,
                body.getPosition().y * Constants.BOX_TO_WORLD,
                2 * FOOTBALLER_RADIUS,
                2 * FOOTBALLER_RADIUS,
                body.getAngle());
        box2DSprite = new Box2DSprite(AssetLoader.footballerTexture);
        box2DSprite.draw(batch,
                leg.getPosition().x * Constants.BOX_TO_WORLD,
                leg.getPosition().y * Constants.BOX_TO_WORLD,
                10, 6,
                leg.getAngle());
    }

    public void setInJump(boolean inJump) {
        this.inJump = inJump;
    }

    public Body getBody() {
        return body;
    }
}
