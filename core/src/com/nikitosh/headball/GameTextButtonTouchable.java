package com.nikitosh.headball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameTextButtonTouchable extends TextButton {
    protected Rectangle bounds;

    public GameTextButtonTouchable(String text) {
        super(text, AssetLoader.gameTextButtonTouchableStyle);
    }

    @Override
    public float getMinWidth() {
        return 0;
    }
}
