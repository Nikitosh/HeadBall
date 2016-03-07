package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.utils.ScreenManager;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class BackButtonTable extends Table {
    private static final String BACK = "Back";

    private Button backButton;

    public BackButtonTable() {
        super();
        initialize();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().disposeCurrentScreen();
            }
        });
    }

    public BackButtonTable(final Runnable runnable) {
        super();
        initialize();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                runnable.run();
                ScreenManager.getInstance().disposeCurrentScreen();
            }
        });
    }

    private void initialize() {
        backButton = new TextButton(BACK, AssetLoader.getGameSkin());
        setFillParent(true);
        add(backButton).top().left().expand().pad(Constants.UI_ELEMENTS_INDENT).row();
    }
}
