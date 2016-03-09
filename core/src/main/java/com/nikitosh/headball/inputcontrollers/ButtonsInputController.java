package com.nikitosh.headball.inputcontrollers;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class ButtonsInputController extends InputController {
    private static final String[] BUTTONS_NAMES = {"Hit", "Jump", "Left", "Right"};

    private final Array<TextButton> buttonsArray = new Array<>();

    private final Move move = new Move();

    public ButtonsInputController() {
        super();

        //used for setting minimum width for buttons inside to 0 (without it they're too wide)
        Array<Container> containersArray = new Array<>();
        for (String name: BUTTONS_NAMES) {
            TextButton button = new TextButton(name, AssetLoader.getGameSkin());
            Container container = new Container<>(button);
            container.minWidth(0);
            buttonsArray.add(button);
            containersArray.add(container);
        }

        leftTable.add(containersArray.get(Constants.HIT)).expand();
        leftTable.add(containersArray.get(Constants.JUMP)).expand();
        rightTable.add(containersArray.get(Constants.LEFT)).expand();
        rightTable.add(containersArray.get(Constants.RIGHT)).expand();
    }

    @Override
    public Move getMove() {
        move.clear();
        for (int i = 0; i < buttonsArray.size; i++) {
            move.setState(i, buttonsArray.get(i).isPressed());
        }
        return move;
    }
}
