package com.nikitosh.headball.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.nikitosh.headball.HeadballGame;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public final class Utilities {
    private static final String LOG_TAG = "Utilities";
    private static final String INCORRECT_RESOURCES_LOADING = "Resources haven't been loaded correctly";

    private Utilities() {}

    private static String readFile(String path) {
        return Gdx.files.internal(path).readString();
    }

    public static JSONObject parseJSONFile(String path) {
        String content = null;
        try {
            content = Utilities.readFile(path);
        } catch (Exception e) {
            Gdx.app.error(LOG_TAG, INCORRECT_RESOURCES_LOADING, e);
            HeadballGame.getActionResolver().showToast(INCORRECT_RESOURCES_LOADING);
        }

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(content);
        }   catch (Exception e) {
            Gdx.app.error(LOG_TAG, INCORRECT_RESOURCES_LOADING, e);
            HeadballGame.getActionResolver().showToast(INCORRECT_RESOURCES_LOADING);
        }
        return jsonObject;
    }

    public static Body getCircleBody(World world, float x, float y, float r, float density, float friction,
                                     float restitution, short category, short mask) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(r);

        FixtureDef fixtureDef = getFixtureDef(shape, friction, density, restitution, category, mask);
        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    public static Body getRectangularBody(World world, float x, float y, float width, float height, float density,
                                          float friction, float restitution, short category, short mask) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x + width / 2, y + height / 2);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = getFixtureDef(shape, friction, density, restitution, category, mask);
        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    private static FixtureDef getFixtureDef(Shape shape, float friction, float density, float restitution,
                                            short category, short mask) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = friction;
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        fixtureDef.filter.categoryBits = category;
        fixtureDef.filter.maskBits = mask;
        return fixtureDef;
    }
}
