package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.widgets.BackButtonTable;

public class AboutScreen extends StageAbstractScreen {
    private static final String ABOUT_TEXT = "This game was developed by Nikitosh & Wowember";

    public AboutScreen() {
        Image background = new Image(AssetLoader.menuTexture);
        background.setFillParent(true);

        Label aboutLabel = new Label(ABOUT_TEXT, AssetLoader.defaultSkin);

        Table aboutTable = new Table();
        aboutTable.setFillParent(true);
        aboutTable.add(aboutLabel);

        stack.addActor(background);
        stack.addActor(new BackButtonTable());
        stack.addActor(aboutTable);
    }
}
