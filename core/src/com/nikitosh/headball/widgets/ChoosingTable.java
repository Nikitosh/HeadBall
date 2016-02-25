package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class ChoosingTable extends Table {
    private static final int COLUMNS_NUMBER = 3;
    private static final Button.ButtonStyle LEFT_ACTIVE = AssetLoader.getGameSkin().get("leftOrangeSlider",
            Button.ButtonStyle.class);
    private static final Button.ButtonStyle LEFT_NOT_ACTIVE = AssetLoader.getGameSkin().get("leftGraySlider",
            Button.ButtonStyle.class);
    private static final Button.ButtonStyle RIGHT_ACTIVE = AssetLoader.getGameSkin().get("rightOrangeSlider",
            Button.ButtonStyle.class);
    private static final Button.ButtonStyle RIGHT_NOT_ACTIVE = AssetLoader.getGameSkin().get("rightGraySlider",
            Button.ButtonStyle.class);

    private Array<Actor> elements = new Array<>();
    protected int currentIndex = 0;
    private Button leftButton;
    private Button rightButton;
    private Actor currentActor;

    public ChoosingTable() {
        super();

        leftButton = new Button(LEFT_NOT_ACTIVE);
        leftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentIndex--;
                currentIndex = Math.max(currentIndex, 0);
                getCell(currentActor).setActor(elements.get(currentIndex));
                currentActor = elements.get(currentIndex);
            }
        });

        rightButton = new Button(RIGHT_NOT_ACTIVE);
        rightButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentIndex++;
                currentIndex = Math.min(currentIndex, elements.size - 1);
                getCell(currentActor).setActor(elements.get(currentIndex));
                currentActor = elements.get(currentIndex);
            }
        });

        currentActor = null;

        defaults().space(Constants.UI_ELEMENTS_INDENT).pad(Constants.UI_ELEMENTS_INDENT);
        add(leftButton).left().expandX();
        add();
        add(rightButton).right().expandX().expandY();
    }


    public void addElement(Actor element) {
        elements.add(element);
        if (elements.size == 1) { //first initialize of table
            currentActor = element;
            for (Cell cell : getCells()) {
                if (!cell.hasActor()) {
                    cell.setActor(currentActor);
                }
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        leftButton.setStyle(currentIndex == 0 ? LEFT_NOT_ACTIVE : LEFT_ACTIVE);
        rightButton.setStyle(currentIndex == elements.size - 1 ? RIGHT_NOT_ACTIVE : RIGHT_ACTIVE);
    }

    public void setOnContinueListener(final Runnable runnable) {
        Button continueButton = new TextButton("Continue", AssetLoader.getGameSkin());
        row();
        add(continueButton).colspan(COLUMNS_NUMBER); //there are COLUMNS_NUMBER cells in upper layer
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                runnable.run();
            }
        });
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
}
