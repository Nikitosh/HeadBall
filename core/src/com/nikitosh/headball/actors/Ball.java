package com.nikitosh.headball.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

public class Ball extends Actor {
    private static final float BALL_RADIUS = 15;

    private Body body;

    public Ball(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x * Constants.WORLD_TO_BOX, y * Constants.WORLD_TO_BOX);
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(BALL_RADIUS * Constants.WORLD_TO_BOX);
        fixtureDef.shape = circleShape;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.6f;
        body.createFixture(fixtureDef);
        circleShape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Box2DSprite box2DSprite = new Box2DSprite(AssetLoader.ballTexture);
        box2DSprite.draw(batch,
                body.getPosition().x * Constants.BOX_TO_WORLD,
                body.getPosition().y * Constants.BOX_TO_WORLD,
                2 * BALL_RADIUS,
                2 * BALL_RADIUS,
                body.getAngle());

    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public static float getBallRadius() {
        return BALL_RADIUS;
    }

    public Body getBody() {
        return body;
    }
}
