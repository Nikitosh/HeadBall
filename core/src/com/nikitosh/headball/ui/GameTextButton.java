package com.nikitosh.headball.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.nikitosh.headball.utils.AssetLoader;

public class GameTextButton extends TextButton {
    protected Rectangle bounds;

    public GameTextButton(String text) {
        super(text, AssetLoader.gameTextButtonStyle);
    }

    @Override
    public float getMinWidth() {
        return 0;
    }
}
