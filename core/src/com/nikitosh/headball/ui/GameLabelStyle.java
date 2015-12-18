package com.nikitosh.headball.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.nikitosh.headball.utils.AssetLoader;

public class GameLabelStyle extends Label.LabelStyle {
    public GameLabelStyle() {
        super();
        font = AssetLoader.font;
    }
}
