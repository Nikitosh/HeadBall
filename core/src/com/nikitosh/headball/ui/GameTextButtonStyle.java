package com.nikitosh.headball.ui;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.nikitosh.headball.utils.AssetLoader;

public class GameTextButtonStyle extends TextButton.TextButtonStyle {
    public GameTextButtonStyle() {
        super();
        up = AssetLoader.skin.getDrawable("red_button02");
        down = AssetLoader.skin.getDrawable("red_button02");
        font = AssetLoader.font;
    }
}
