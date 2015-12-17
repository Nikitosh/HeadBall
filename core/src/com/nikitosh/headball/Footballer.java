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
    private static final float FOOTBALLER_RADIUS = 20;
    private static final float FOOTBALLER_SPEED = 50f;
    private static final float FOOTBALLER_JUMP = 100f;

    private ShapeRenderer shapeRenderer;
    private Body body, body2;
    private boolean inJump = false;
    private Fixture rectangleFixture;

    public Footballer(World world, float x, float y) {
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

        BodyDef rectangleBodyDef = new BodyDef();
        rectangleBodyDef.type = BodyDef.BodyType.DynamicBody;
        rectangleBodyDef.fixedRotation = true;
        rectangleBodyDef.position.set((x - 20) * Constants.WORLD_TO_BOX, y * Constants.WORLD_TO_BOX );
        body2 = world.createBody(rectangleBodyDef);

        FixtureDef rectangleFixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(3 * Constants.WORLD_TO_BOX, 3 * Constants.WORLD_TO_BOX);
        rectangleFixtureDef.shape = polygonShape;
        rectangleFixtureDef.density = 0.1f;
        rectangleFixture = body2.createFixture(rectangleFixtureDef);
        polygonShape.dispose();

        DistanceJointDef jointDef = new DistanceJointDef();
        //jointDef.bodyA = body;
        //jointDef.bodyB = body2;
        jointDef.initialize(body, body2, body.getPosition(), body2.getPosition());
        //jointDef.localAnchorB = body.getPosition();
        jointDef.length = 20 * Constants.WORLD_TO_BOX;
        body2.setAngularVelocity(0f);

        world.createJoint(jointDef);


        shapeRenderer = new ShapeRenderer();
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
            body2.setLinearVelocity(-FOOTBALLER_SPEED * Constants.WORLD_TO_BOX, body2.getLinearVelocity().y);
        }
        if (move.isRight() && !move.isLeft()) {
            Gdx.app.log("Footballer", "Right");
            body.setLinearVelocity(FOOTBALLER_SPEED * Constants.WORLD_TO_BOX, body.getLinearVelocity().y);
            body2.setLinearVelocity(FOOTBALLER_SPEED * Constants.WORLD_TO_BOX, body2.getLinearVelocity().y);
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
        body2.setTransform(body2.getPosition(), 0f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        Box2DSprite box2DSprite = new Box2DSprite(AssetLoader.footballerTexture);
        box2DSprite.draw(batch,
                body.getPosition().x * Constants.BOX_TO_WORLD,
                body.getPosition().y * Constants.BOX_TO_WORLD,
                2 * FOOTBALLER_RADIUS,
                2 * FOOTBALLER_RADIUS,
                body.getAngle());


        Vector2 vector = position(rectangleFixture);
        box2DSprite = new Box2DSprite(AssetLoader.legTexture);
        box2DSprite.draw(batch,
                vector.x * Constants.BOX_TO_WORLD,
                vector.y * Constants.BOX_TO_WORLD,
                width(rectangleFixture) * Constants.BOX_TO_WORLD,
                height(rectangleFixture) * Constants.BOX_TO_WORLD,
                rectangleFixture.getBody().getAngle());

        //batch.draw(new Texture(Gdx.files.internal("images/splashBall.jpg")), );

        Gdx.app.log("Footballer", Float.toString(body.getPosition().x));
        Gdx.app.log("Footballer", Float.toString(body.getPosition().y));
        Gdx.app.log("Foo", Float.toString(body2.getAngle()));

        body2.setAngularVelocity(0);
    }

    public void setInJump(boolean inJump) {
        this.inJump = inJump;
    }

    public Body getBody() {
        return body;
    }
}
