package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.nikitosh.headball.utils.AssetLoader;

public class BackgroundStageAbstractScreen extends StageAbstractScreen {
    public BackgroundStageAbstractScreen() {
        Image background = new Image(AssetLoader.menuTexture);
        background.setFillParent(true);
        stack.addActor(background);
    }
}
