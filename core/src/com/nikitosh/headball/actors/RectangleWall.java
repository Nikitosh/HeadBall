package com.nikitosh.headball.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.Utilities;

public class RectangleWall extends Wall {
    private final static float RECTANGLE_WALL_DENSITY = 1f;
    private final static float RECTANGLE_WALL_FRICTION = 1f;
    private final static float RECTANGLE_WALL_RESTITUTION = 0f;
    private float width;
    private float height;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public RectangleWall(World world, float x, float y, float width, float height) {
        this.width = width;
        this.height = height;

        body = Utilities.getRectangularBody(world,
                x * Constants.WORLD_TO_BOX, y * Constants.WORLD_TO_BOX,
                width * Constants.WORLD_TO_BOX, height * Constants.WORLD_TO_BOX,
                RECTANGLE_WALL_DENSITY, RECTANGLE_WALL_FRICTION, RECTANGLE_WALL_RESTITUTION,
                Constants.GAME_OBJECT_CATEGORY, Constants.GAME_OBJECT_MASK);
        body.setType(BodyDef.BodyType.StaticBody);
        body.getFixtureList().get(0).setUserData(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.translate(getX(), getY(), 0);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(body.getPosition().x * Constants.BOX_TO_WORLD - width / 2,
                body.getPosition().y * Constants.BOX_TO_WORLD - height / 2,
                width, height);
        shapeRenderer.end();

        batch.begin();
    }
}
