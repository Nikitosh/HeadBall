package com.nikitosh.headball.inputcontrollers;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class TouchpadInputController extends InputController {
    private static final String HIT_BUTTON_NAME = "Hit";
    private static final float TOUCHPAD_KNOB = 0;
    private static final float TOUCHPAD_JUMP_LEVEL_PERCENTAGE = 0.5f;

    private TextButton hitButton;
    private Touchpad touchpad;

    private Move move = new Move();

    public TouchpadInputController() {
        super();

        hitButton = new TextButton(HIT_BUTTON_NAME, AssetLoader.getGameSkin());
        touchpad = new Touchpad(TOUCHPAD_KNOB, AssetLoader.getGameSkin());

        leftTable.add(hitButton).pad(Constants.UI_ELEMENTS_INDENT);
        rightTable.add(touchpad).width(Constants.UI_LAYER_HEIGHT).height(Constants.UI_LAYER_HEIGHT);
    }

    @Override
    public Move getMove() {
        move.clear();
        if (hitButton.isPressed()) {
            move.setState(Constants.HIT, true);
        }
        if (touchpad.getKnobPercentX() > 0) {
            move.setState(Constants.RIGHT, true);
        }
        if (touchpad.getKnobPercentX() < 0) {
            move.setState(Constants.LEFT, true);
        }
        if (touchpad.getKnobPercentY() >= TOUCHPAD_JUMP_LEVEL_PERCENTAGE) {
            move.setState(Constants.JUMP, true);
        }
        return move;
    }
}
