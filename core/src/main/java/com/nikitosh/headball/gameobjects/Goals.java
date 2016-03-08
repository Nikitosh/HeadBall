package com.nikitosh.headball.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.Utilities;

public class Goals {
    private static final float GOALS_DENSITY = 1f;
    private static final float GOALS_FRICTION = 0f;
    private static final float GOALS_RESTITUTION = 0f;

    private final Body body;

    private final float x;
    private final float y;
    private final float width;
    private final float goalsHeight;
    private final float crossbarHeight;
    private final boolean isLeftSided; //are the goals left-sided or not

    public Goals(World world, float x, float y, float width, float goalsHeight, float crossbarHeight,
                 boolean isLeftSided) {
        this.width = width;
        this.goalsHeight = goalsHeight;
        this.crossbarHeight = crossbarHeight;
        this.isLeftSided = isLeftSided;
        this.x = x;
        this.y = y;

        body = Utilities.getRectangularBody(world,
                x * Constants.WORLD_TO_BOX, (y + goalsHeight) * Constants.WORLD_TO_BOX,
                width * Constants.WORLD_TO_BOX, crossbarHeight * Constants.WORLD_TO_BOX,
                GOALS_DENSITY, GOALS_FRICTION, GOALS_RESTITUTION,
                Constants.GAME_OBJECT_CATEGORY, Constants.GAME_OBJECT_MASK);
        body.setType(BodyDef.BodyType.StaticBody);
        body.getFixtureList().get(0).setUserData(this);
    }

    public boolean contains(Vector2 point) {
        if (isLeftSided) {
            return point.x < body.getPosition().x + width / 2 * Constants.WORLD_TO_BOX
                    && point.y < body.getPosition().y;
        } else {
            return point.x > body.getPosition().x - width / 2 * Constants.WORLD_TO_BOX
                    && point.y < body.getPosition().y;
        }
    }

    public boolean containsOnUpperEdge(Vector2 point, float radius) {
        point.x *= Constants.BOX_TO_WORLD;
        point.y *= Constants.BOX_TO_WORLD;
        radius *= Constants.BOX_TO_WORLD;
        return Math.abs(point.y - (y + goalsHeight + crossbarHeight)) <= radius
                && point.x >= x && point.x <= x + width;
        }

    public Vector2 getToFieldCenterVector() {
        if (isLeftSided) {
            return new Vector2(1, 0);
        } else {
            return new Vector2(-1, 0);
        }
    }

    public Vector2 getPosition() {
        return new Vector2(x + width / 2, y + (goalsHeight + crossbarHeight) / 2);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return goalsHeight + crossbarHeight;
    }
}
