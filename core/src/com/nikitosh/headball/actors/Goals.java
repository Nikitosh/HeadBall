package com.nikitosh.headball.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

public class Goals extends Actor {
    private float width;
    private float height;

    private enum Side {LEFT, RIGHT};

    private Body body;
    private Side side;

    public Goals(World world, float x, float y, float width, float height, boolean left) {
        this.width = width;
        this.height = height;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((x + width / 2) * Constants.WORLD_TO_BOX, (y + height / 2) * Constants.WORLD_TO_BOX);
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width * Constants.WORLD_TO_BOX / 2, height * Constants.WORLD_TO_BOX / 2);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        polygonShape.dispose();

        if (left) {
            side = Side.LEFT;
        }
        else {
            side = Side.RIGHT;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Box2DSprite box2DSprite;
        if (side == Side.LEFT) {
            box2DSprite = new Box2DSprite(AssetLoader.goalsTexture);
        }
        else {
            box2DSprite = new Box2DSprite(AssetLoader.reversedGoalsTexture);
        }
        box2DSprite.draw(batch,
                    body.getPosition().x * Constants.BOX_TO_WORLD,
                    body.getPosition().y * Constants.BOX_TO_WORLD - Constants.GOALS_HEIGHT / 2,
                    width,
                    Constants.GOALS_HEIGHT + height,
                    body.getAngle());
    }

    public boolean contains(Vector2 point) {
        if (side == Side.LEFT) {
            return point.x < body.getPosition().x + width / 2 * Constants.WORLD_TO_BOX && point.y < body.getPosition().y;
        }
        else {
            return point.x > body.getPosition().x - width / 2 * Constants.WORLD_TO_BOX && point.y < body.getPosition().y;
        }
    }
}
