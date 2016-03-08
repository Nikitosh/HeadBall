package com.nikitosh.headball.inputcontrollers;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nikitosh.headball.Move;

public abstract class InputController {
    protected final Table leftTable = new Table();
    protected final Table rightTable = new Table();

    public abstract Move getMove();

    public Table getLeftTable() {
        return leftTable;
    }

    public Table getRightTable() {
        return rightTable;
    }
}
