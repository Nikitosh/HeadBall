package com.nikitosh.headball.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.nikitosh.headball.utils.AssetLoader;

public class GameWindowStyle extends Window.WindowStyle {
    public GameWindowStyle() {
        titleFont = AssetLoader.font;
        background = new Image(AssetLoader.windowBackground).getDrawable();
    }
}
