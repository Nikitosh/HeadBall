package com.nikitosh.headball.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.Utilities;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

public class Ball extends Actor {
    private static final float BALL_RADIUS = 10;
    private static final float BALL_DENSITY = 1f;
    private static final float BALL_FRICTION = 0.1f;
    private static final float BALL_RESTITUTION = 0.6f;

    private Body body;
    private Box2DSprite ballSprite;

    public Ball(World world, float x, float y) {
        body = Utilities.getCircleBody(world,
                x * Constants.WORLD_TO_BOX, y * Constants.WORLD_TO_BOX, BALL_RADIUS * Constants.WORLD_TO_BOX,
                BALL_DENSITY, BALL_FRICTION, BALL_RESTITUTION,
                Constants.GAME_OBJECT_CATEGORY, Constants.GAME_OBJECT_MASK);

        ballSprite = new Box2DSprite(AssetLoader.ballTexture);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        ballSprite.draw(batch,
                body.getPosition().x * Constants.BOX_TO_WORLD, body.getPosition().y * Constants.BOX_TO_WORLD,
                2 * BALL_RADIUS, 2 * BALL_RADIUS, body.getAngle());
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

    public void setInitialPosition(float positionX, float positionY) {
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
        body.setTransform(positionX * Constants.WORLD_TO_BOX, positionY * Constants.WORLD_TO_BOX, 0);
    }
}
