package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.utils.Constants;

public class ChoosingTable extends Table {

    private Array <Actor> elements = new Array<>();
    protected int currentIndex = 0;
    private Button leftButton;
    private Button rightButton;
    private Button continueButton;
    private Actor currentActor;

    public ChoosingTable() {
        super();

        leftButton = new GameTextButtonTouchable("Left");
        leftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentIndex--;
                getCell(currentActor).setActor(elements.get(currentIndex));
                currentActor = elements.get(currentIndex);
            }
        });

        rightButton = new GameTextButtonTouchable("Right");
        rightButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentIndex++;
                getCell(currentActor).setActor(elements.get(currentIndex));
                currentActor = elements.get(currentIndex);
            }
        });

        continueButton = new GameTextButtonTouchable("Continue");

        currentActor = null;

        defaults().space(Constants.UI_ELEMENTS_INDENT);
        add(leftButton).left().expandX();
        add();
        add(rightButton).right().expandX();
        row();
        add(continueButton).colspan(3).bottom();
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
        leftButton.setVisible(currentIndex != 0);
        rightButton.setVisible(currentIndex != elements.size - 1);
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
