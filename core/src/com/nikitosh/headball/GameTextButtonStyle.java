package com.nikitosh.headball;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameTextButtonStyle extends TextButton.TextButtonStyle {
    public GameTextButtonStyle() {
        super();
        up = AssetLoader.skin.getDrawable("blue_button00");
        down = AssetLoader.skin.getDrawable("blue_button01");
        pressedOffsetX = 1;
        pressedOffsetY = -1;
        font = AssetLoader.font;
    }
}
