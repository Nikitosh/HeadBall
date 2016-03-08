package com.nikitosh.headball.gameobjects;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.Utilities;

public class RectangleWall extends Wall {
    private static final float RECTANGLE_WALL_DENSITY = 1f;
    private static final float RECTANGLE_WALL_HORIZONTAL_FRICTION = 0.5f;
    private static final float RECTANGLE_WALL_VERTICAL_FRICTION = 0;
    private static final float RECTANGLE_WALL_RESTITUTION = 0;

    private final float x;
    private final float y;
    private final float width;
    private final float height;

    public RectangleWall(World world, float x, float y, float width, float height, boolean isHorizontal) {
        this.x = x;
        this.y = y;
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

    public Array<Float> getRectangle() {
        return new Array<>(new Float[] {x, y, width, height});
    }
}
