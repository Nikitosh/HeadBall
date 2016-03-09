package com.nikitosh.headball.inputcontrollers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.nikitosh.headball.Move;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class AccelerometerInputController extends InputController {
    private static final String JUMP = "Jump";
    private static final String HIT = "Hit";
    private static final float MINIMUM_ACCELERATE_VALUE = 1f;

    private final Move move = new Move();

    private final TextButton jumpButton;
    private final TextButton hitButton;

    public AccelerometerInputController() {
        super();

        jumpButton = new TextButton(JUMP, AssetLoader.getGameSkin());
        hitButton = new TextButton(HIT, AssetLoader.getGameSkin());

        leftTable.add(jumpButton);
        rightTable.add(hitButton);
    }

    @Override
    public Move getMove() {
        move.clear();
        move.setState(Constants.JUMP, jumpButton.isPressed());
        move.setState(Constants.HIT, hitButton.isPressed());

        float accelerateY = Gdx.input.getAccelerometerY();
        move.setState(Constants.LEFT, accelerateY < -MINIMUM_ACCELERATE_VALUE);
        move.setState(Constants.RIGHT, accelerateY > MINIMUM_ACCELERATE_VALUE);
        return move;
    }
}
