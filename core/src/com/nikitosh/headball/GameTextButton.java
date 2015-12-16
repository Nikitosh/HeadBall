package com.nikitosh.headball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameTextButton extends TextButton {
    protected Rectangle bounds;

    public GameTextButton(String text, boolean touchable) {
        super(text, AssetLoader.gameTextButtonStyle);
        setStyle(AssetLoader.gameTextButtonStyle);
        if (!touchable) {
            getStyle().down = getStyle().up;
            getStyle().pressedOffsetX = 0;
            getStyle().pressedOffsetY = 0;
        }
    }

    public GameTextButton(String text) {
        this(text, true);
    }

    @Override
    public float getMinWidth() {
        return 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
