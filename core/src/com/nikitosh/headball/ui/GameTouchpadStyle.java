package com.nikitosh.headball.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.nikitosh.headball.utils.AssetLoader;

public class GameTouchpadStyle extends Touchpad.TouchpadStyle {
    public GameTouchpadStyle() {
        super();
        background = AssetLoader.touchpadBackgroundDrawable;
        knob = AssetLoader.touchpadKnobDrawable;
    }

}
