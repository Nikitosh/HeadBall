package com.nikitosh.headball.gameobjects;

import com.badlogic.gdx.physics.box2d.Body;
import com.nikitosh.headball.utils.Constants;

public abstract class GameObject {

    protected Body body;
    protected float radius;

    public String serialise() {
        return Float.toString(body.getPosition().x) + ' ' + Float.toString(body.getPosition().y) + ' '
                + Float.toString(body.getAngle()) + ' ' + Float.toString(radius) + Constants.DATA_SEPARATOR;
    }

}
