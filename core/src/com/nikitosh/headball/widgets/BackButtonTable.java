package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.ScreenManager;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.utils.Constants;

public class BackButtonTable extends Table {
    private static final String BACK = "Back";

    public BackButtonTable() {
        super();
        Button backButton = new GameTextButtonTouchable(BACK);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().disposeCurrentScreen();
            }
        });

        setFillParent(true);
        add(backButton).top().left().expand().pad(Constants.UI_ELEMENTS_INDENT).row();
    }

    public BackButtonTable(final Runnable runnable) {
        super();
        Button backButton = new GameTextButtonTouchable(BACK);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                runnable.run();
                ScreenManager.getInstance().disposeCurrentScreen();
            }
        });

        setFillParent(true);
        add(backButton).top().left().expand().pad(Constants.UI_ELEMENTS_INDENT).row();
    }
}
