package com.nikitosh.headball.controllers;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class ButtonsInputController implements InputController {

    private static final String[] BUTTONS_NAMES = {"Hit", "Jump", "Left", "Right"};

    private Table uiTable = new Table();
    private Array<TextButton> buttonsArray = new Array<>();

    private Move move = new Move();

    public ButtonsInputController(Table infoTable) {

        //used for setting minimum width for buttons inside to 0 (without it they're too wide)
        Array<Container> containersArray = new Array<>();
        for (String name: BUTTONS_NAMES) {
            TextButton button = new TextButton(name, AssetLoader.getGameSkin());
            Container container = new Container<>(button);
            container.minWidth(0);
            buttonsArray.add(button);
            containersArray.add(container);
        }

        uiTable.add(containersArray.get(Constants.HIT)).expand().bottom();
        uiTable.add(containersArray.get(Constants.JUMP)).expand().bottom();
        uiTable.add(infoTable).expand().bottom();
        uiTable.add(containersArray.get(Constants.LEFT)).expand().bottom();
        uiTable.add(containersArray.get(Constants.RIGHT)).expand().bottom();

    }

    @Override
    public Move getMove() {
        move.clear();
        for (int i = 0; i < buttonsArray.size; i++) {
            if (buttonsArray.get(i).isPressed()) {
                move.setState(i, true);
            }
        }
        return move;
    }

    @Override
    public Table getTable() {
        return uiTable;
    }
}
