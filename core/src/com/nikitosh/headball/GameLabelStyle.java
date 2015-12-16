package com.nikitosh.headball;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class GameLabelStyle extends Label.LabelStyle {
    public GameLabelStyle() {
        super();
        font = AssetLoader.font;
    }
}
