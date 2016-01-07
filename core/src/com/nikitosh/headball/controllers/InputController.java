package com.nikitosh.headball.controllers;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nikitosh.headball.Move;

public interface InputController {
    Move getMove();
    Table getTable();
}
