package com.nikitosh.headball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class RectangleWall extends Wall {
    private float width;
    private float height;

    private ShapeRenderer shapeRenderer;

    public RectangleWall(World world, float x, float y, float width, float height) {
        this.width = width;
        this.height = height;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x * Constants.WORLD_TO_BOX + width / 2 * Constants.WORLD_TO_BOX, y * Constants.WORLD_TO_BOX + height / 2 * Constants.WORLD_TO_BOX);
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width * Constants.WORLD_TO_BOX / 2, height * Constants.WORLD_TO_BOX / 2);
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        Fixture fixture = body.createFixture(fixtureDef);
        Gdx.app.log("WWall", fixture.toString());
        fixture.setUserData(this);
        polygonShape.dispose();

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.translate(getX(), getY(), 0);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(body.getPosition().x * Constants.BOX_TO_WORLD - width / 2, body.getPosition().y * Constants.BOX_TO_WORLD - height / 2, width, height);
        shapeRenderer.end();

        batch.begin();

        Gdx.app.log("Wall1", Float.toString(body.getPosition().x * Constants.BOX_TO_WORLD));
        Gdx.app.log("Wall2", Float.toString(body.getPosition().y * Constants.BOX_TO_WORLD));
        Gdx.app.log("Wall3", Float.toString(body.getPosition().x * Constants.BOX_TO_WORLD + width));
        Gdx.app.log("Wall4", Float.toString(body.getPosition().y * Constants.BOX_TO_WORLD + height));
    }
}
