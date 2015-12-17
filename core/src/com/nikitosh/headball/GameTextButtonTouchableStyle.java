package com.nikitosh.headball;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameTextButtonTouchableStyle extends TextButton.TextButtonStyle {
    public GameTextButtonTouchableStyle() {
        super();
        up = AssetLoader.skin.getDrawable("blue_button02");
        down = AssetLoader.skin.getDrawable("blue_button03");
        pressedOffsetX = 1;
        pressedOffsetY = -1;
        font = AssetLoader.font;
    }
}
