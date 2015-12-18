package com.nikitosh.headball.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.nikitosh.headball.utils.AssetLoader;

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
