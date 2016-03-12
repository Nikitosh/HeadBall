package com.nikitosh.headball.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import java.util.Map;
import java.util.TreeMap;

public class GameFieldGroup extends Group {
    private final Map<String, Float> spriteX = new TreeMap<>();
    private final Map<String, Float> spriteY = new TreeMap<>();
    private final Map<String, Float> spriteWidth = new TreeMap<>();
    private final Map<String, Float> spriteHeight = new TreeMap<>();
    private final Map<String, Float> spriteAngle = new TreeMap<>();
    private final Map<String, Box2DSprite> spriteMap = new TreeMap<>();

    private Array<Array<Float>> walls;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public GameFieldGroup() {
        super();
        Image field = new Image(AssetLoader.getFieldTexture());
        field.setSize(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT - Constants.UI_LAYER_HEIGHT);
        addActor(field);
        setSize(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT - Constants.UI_LAYER_HEIGHT);
        spriteMap.put(Constants.BALL, AssetLoader.getBallSprite());
        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            spriteMap.put(Constants.FOOTBALLER + i, AssetLoader.getFootballerSprite(i));
            spriteMap.put(Constants.LEG + i, AssetLoader.getLegSprite(i));
            spriteMap.put(Constants.GOALS + i, AssetLoader.getGoalsSprite(i));
        }
    }

    @Override
    public void drawChildren(Batch batch, float parentAlpha) {
        super.drawChildren(batch, parentAlpha);
        drawWalls(batch);
        draw(batch, Constants.BALL);
        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            draw(batch, Constants.FOOTBALLER + i);
            draw(batch, Constants.LEG + i);
        }
        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            draw(batch, Constants.GOALS + i);
        }
    }

    private void drawWalls(Batch batch) {
        batch.end();

        //magic code for using ShapeRenderer on given batch
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        for (Array<Float> wall : walls) {
            shapeRenderer.rect(wall.get(0), wall.get(1), wall.get(2), wall.get(3));
        }
        shapeRenderer.end();
        batch.begin();
    }

    private void draw(Batch batch, String spriteName) {
        Box2DSprite sprite = spriteMap.get(spriteName);
        sprite.draw(batch, spriteX.get(spriteName), spriteY.get(spriteName),
                spriteWidth.get(spriteName), spriteHeight.get(spriteName), spriteAngle.get(spriteName));
    }

    public void setSpritePosition(String spriteName, float x, float y, float width, float height, float angle) {
        spriteX.put(spriteName, x);
        spriteY.put(spriteName, y);
        spriteWidth.put(spriteName, width);
        spriteHeight.put(spriteName, height);
        spriteAngle.put(spriteName, angle);
    }

    public void setWallsPosition(Array<Array<Float>> walls) {
        this.walls = walls;
    }
}
