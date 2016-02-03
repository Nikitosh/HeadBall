package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nikitosh.headball.jsonReaders.TeamsReader;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.widgets.BackButtonTable;
import com.nikitosh.headball.widgets.ChoosingTable;

public class TeamChoosingScreen implements Screen {

    private Stage stage;

    public TeamChoosingScreen(final Game game, final Screen previousScreen, final Tournament tournament) {
        ChoosingTable choosingTable = new ChoosingTable();

        final TeamsReader reader = new TeamsReader();
        for (int j = 0; j < tournament.getParticipants().size; j++) {
            for (int i = 0; i < reader.getTeamsNumber(); i++) {
                if (tournament.getParticipants().get(j).getName().equals(reader.getTeamName(i))) {
                    Table teamCell = new Table();

                    ImageButton teamImage = new ImageButton(AssetLoader.teamsSkin.getDrawable(reader.getTeamIconName(i)));
                    teamCell.add(teamImage).pad(Constants.UI_ELEMENTS_INDENT).row();
                    final int index = j;
                    teamImage.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            game.setScreen(new TournamentScreen(game, tournament, tournament.getParticipants().get(index)));
                        }
                    });

                    Label teamName = new Label(reader.getTeamName(i), AssetLoader.gameLabelStyle);
                    teamCell.add(teamName).pad(Constants.UI_ELEMENTS_INDENT).row();

                    choosingTable.addElement(teamCell);
                }
                choosingTable.setFillParent(true);

                Stack stack = new Stack();
                stack.setFillParent(true);
                stack.addActor(choosingTable);
                stack.addActor(new BackButtonTable(game, this, previousScreen));

                stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));
                stage.addActor(stack);
            }
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
