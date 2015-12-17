package com.nikitosh.headball;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class GameWindowStyle extends Window.WindowStyle {
    GameWindowStyle() {
        titleFont = AssetLoader.font;
        background = new Image(AssetLoader.windowBackground).getDrawable();
    }
}
