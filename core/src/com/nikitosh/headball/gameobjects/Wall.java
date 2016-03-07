package com.nikitosh.headball.gameobjects;

import com.badlogic.gdx.physics.box2d.Body;

public abstract class Wall {
    protected Body body;

    public Body getBody() {
        return body;
    }
}
