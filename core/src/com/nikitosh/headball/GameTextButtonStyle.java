package com.nikitosh.headball;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameTextButtonStyle extends TextButton.TextButtonStyle {
    public GameTextButtonStyle() {
        super();
        up = AssetLoader.skin.getDrawable("blue_button05");
        down = AssetLoader.skin.getDrawable("blue_button05");
        font = AssetLoader.font;
    }
}
