package com.nikitosh.headball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import static net.dermetfan.gdx.physics.box2d.Box2DUtils.height;
import static net.dermetfan.gdx.physics.box2d.Box2DUtils.position;
import static net.dermetfan.gdx.physics.box2d.Box2DUtils.width;

public class Footballer extends Actor {
    private static final float FOOTBALLER_RADIUS = 35;
    private static final float FOOTBALLER_SPEED = 50f;
    private static final float FOOTBALLER_JUMP = 100f;

    private Body body;
    private boolean inJump = false;
    private boolean left;

    public Footballer(World world, float x, float y, boolean left) {
        this.left = left;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x * Constants.WORLD_TO_BOX, y * Constants.WORLD_TO_BOX);
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(FOOTBALLER_RADIUS * Constants.WORLD_TO_BOX);
        fixtureDef.shape = circleShape;
        fixtureDef.density = 2f;
        fixtureDef.friction = 0.1f;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        circleShape.dispose();
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
        Gdx.app.log("Footballer", "update");
        if (move.isLeft() && !move.isRight()) {
            Gdx.app.log("Footballer", "Left");
            body.setLinearVelocity(-FOOTBALLER_SPEED * Constants.WORLD_TO_BOX, body.getLinearVelocity().y);
        }
        if (move.isRight() && !move.isLeft()) {
            Gdx.app.log("Footballer", "Right");
            body.setLinearVelocity(FOOTBALLER_SPEED * Constants.WORLD_TO_BOX, body.getLinearVelocity().y);
        }
        if (!move.isLeft() && !move.isRight()) {
            Gdx.app.log("Footballer", "Stop");
            body.setLinearVelocity(body.getLinearVelocity().x / 2, body.getLinearVelocity().y);
        }
        if (inJump == false && move.isJump()) {
            inJump = true;
            body.setLinearVelocity(body.getLinearVelocity().x, FOOTBALLER_JUMP * Constants.WORLD_TO_BOX);
        }
        if (move.isHit()) {
            //rectangleFixture;
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
    }

    public void setInJump(boolean inJump) {
        this.inJump = inJump;
    }

    public Body getBody() {
        return body;
    }
}
