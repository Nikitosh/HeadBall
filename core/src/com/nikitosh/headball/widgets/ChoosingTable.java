package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class ChoosingTable extends Table {
    //should be moved to ui.json
    private static final Drawable LEFT_ORANGE = AssetLoader.skin.getDrawable("red_sliderLeft");
    private static final Button.ButtonStyle LEFT_ACTIVE = new Button.ButtonStyle(LEFT_ORANGE, LEFT_ORANGE, LEFT_ORANGE);
    private static final Drawable RIGHT_ORANGE = AssetLoader.skin.getDrawable("red_sliderRight");
    private static final Button.ButtonStyle RIGHT_ACTIVE = new Button.ButtonStyle(RIGHT_ORANGE, RIGHT_ORANGE, RIGHT_ORANGE);
    private static final Drawable LEFT_GRAY = AssetLoader.skin.getDrawable("grey_sliderLeft");
    private static final Button.ButtonStyle LEFT_NOT_ACTIVE = new Button.ButtonStyle(LEFT_GRAY, LEFT_GRAY, LEFT_GRAY);
    private static final Drawable RIGHT_GRAY = AssetLoader.skin.getDrawable("grey_sliderRight");
    private static final Button.ButtonStyle RIGHT_NOT_ACTIVE = new Button.ButtonStyle(RIGHT_GRAY, RIGHT_GRAY, RIGHT_GRAY);

    private Array <Actor> elements = new Array<>();
    protected int currentIndex = 0;
    private Button leftButton;
    private Button rightButton;
    private Button continueButton;
    private Actor currentActor;

    public ChoosingTable() {
        super();

        leftButton = new Button();
        leftButton.setStyle(LEFT_ACTIVE);
        leftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentIndex--;
                currentIndex = Math.max(currentIndex, 0);
                getCell(currentActor).setActor(elements.get(currentIndex));
                currentActor = elements.get(currentIndex);
            }
        });

        rightButton = new Button();
        rightButton.setStyle(RIGHT_ACTIVE);
        rightButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentIndex++;
                currentIndex = Math.min(currentIndex, elements.size - 1);
                getCell(currentActor).setActor(elements.get(currentIndex));
                currentActor = elements.get(currentIndex);
            }
        });

        continueButton = new GameTextButtonTouchable("Continue");

        currentActor = null;

        defaults().space(Constants.UI_ELEMENTS_INDENT).pad(Constants.UI_ELEMENTS_INDENT);
        add(leftButton).left().expandX();
        add();
        add(rightButton).right().expandX().expandY();
        row();
        add(continueButton).colspan(3).center();
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
