package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.AssetLoader;

public interface ResultTable {
    Label.LabelStyle HIGHLIGHTED_STYLE = AssetLoader.defaultSkin.get("highlighted", Label.LabelStyle.class);
    Group getTable();
    void highlightTeam(Team team);
}
