package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.widgets.BackButtonTable;

public class AboutScreen extends BackgroundStageAbstractScreen {
    private static final String ABOUT_TEXT = "This game was developed by Nikitosh & Wowember";

    public AboutScreen() {
        Label aboutLabel = new Label(ABOUT_TEXT, AssetLoader.gameSkin, "background");

        Table aboutTable = new Table();
        aboutTable.setFillParent(true);
        aboutTable.add(aboutLabel);

        stack.addActor(new BackButtonTable());
        stack.addActor(aboutTable);
    }
}
