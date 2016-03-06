package com.nikitosh.headball.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.Utilities;

public class RectangleWall extends Wall {
    private static final float RECTANGLE_WALL_DENSITY = 1f;
    private static final float RECTANGLE_WALL_HORIZONTAL_FRICTION = 0.5f;
    private static final float RECTANGLE_WALL_VERTICAL_FRICTION = 0;
    private static final float RECTANGLE_WALL_RESTITUTION = 0;

    private float width;
    private float height;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public RectangleWall(World world, float x, float y, float width, float height, boolean isHorizontal) {
        this.width = width;
        this.height = height;

        float friction;
        if (isHorizontal) {
            friction = RECTANGLE_WALL_HORIZONTAL_FRICTION;
        } else {
            friction = RECTANGLE_WALL_VERTICAL_FRICTION;
        }

        body = Utilities.getRectangularBody(world,
                x * Constants.WORLD_TO_BOX, y * Constants.WORLD_TO_BOX,
                width * Constants.WORLD_TO_BOX, height * Constants.WORLD_TO_BOX,
                RECTANGLE_WALL_DENSITY, friction, RECTANGLE_WALL_RESTITUTION,
                Constants.GROUND_WALL_CATEGORY, Constants.GROUND_WALL_MASK);
        body.setType(BodyDef.BodyType.StaticBody);
        body.getFixtureList().get(0).setUserData(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        //magic code for using ShapeRenderer on given batch
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
