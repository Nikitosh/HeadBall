package com.nikitosh.headball.utils;

import com.badlogic.gdx.physics.box2d.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utilities {
    public static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static Body getCircleBody(World world, float x, float y, float r, float density, float friction, float restitution) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(r);

        FixtureDef fixtureDef = getFixtureDef(shape, friction, density, restitution);
        fixtureDef.filter.maskBits = 15;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    public static Body getRectangularBody(World world, float x, float y, float width, float height,
                                          float density, float friction, float restitution) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x + width / 2, y + height / 2);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = getFixtureDef(shape, friction, density, restitution);
        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
    }

    private static FixtureDef getFixtureDef(Shape shape, float friction, float density, float restitution) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = friction;
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
        return fixtureDef;
    }
}
