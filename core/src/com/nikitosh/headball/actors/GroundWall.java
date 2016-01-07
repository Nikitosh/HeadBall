package com.nikitosh.headball.actors;

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.nikitosh.headball.utils.Constants;

public class GroundWall extends RectangleWall {
    public GroundWall(World world, float x, float y, float width, float height) {
        super(world, x, y, width, height);
        Filter filter = new Filter();
        filter.categoryBits = Constants.GROUND_WALL_CATEGORY;
        filter.maskBits = Constants.GROUND_WALL_MASK;
        body.getFixtureList().get(0).setFilterData(filter);
    }
}
