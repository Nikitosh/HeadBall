package com.nikitosh.headball.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.nikitosh.headball.utils.AssetLoader;

public class GameButtonStyle extends Button.ButtonStyle {
    public GameButtonStyle(String name) {
        this(name, name);
    }
    public GameButtonStyle(String upName, String downName) {
        super();
        up = AssetLoader.skin.getDrawable(upName);
        down = AssetLoader.skin.getDrawable(downName);
        pressedOffsetX = 1;
        pressedOffsetY = -1;
    }
}
