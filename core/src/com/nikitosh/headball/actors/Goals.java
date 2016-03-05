package com.nikitosh.headball.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.Utilities;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

public class Goals extends Actor {
    private static final float GOALS_DENSITY = 1f;
    private static final float GOALS_FRICTION = 0f;
    private static final float GOALS_RESTITUTION = 0f;

    private Body body;

    private float x;
    private float y;
    private float width;
    private float goalsHeight;
    private float crossbarHeight;
    private boolean isLeftSided; //are the goals left-sided or not

    private Box2DSprite goalsSprite;

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

        if (isLeftSided) {
            goalsSprite = new Box2DSprite(AssetLoader.getGoalsTexture());
        } else {
            goalsSprite = new Box2DSprite(AssetLoader.getReversedGoalsTexture());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        goalsSprite.draw(batch,
                body.getPosition().x * Constants.BOX_TO_WORLD,
                body.getPosition().y * Constants.BOX_TO_WORLD - goalsHeight / 2,
                width, goalsHeight + crossbarHeight, body.getAngle());
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
}
