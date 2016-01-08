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
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.widgets.BackButtonTable;
import com.nikitosh.headball.widgets.PagedScrollPane;
import com.nikitosh.headball.jsonReaders.TournamentsReader;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class TournamentChoosingScreen implements Screen {
    private static final float FLING_TIME = 0.1f;
    private static final String JSON_LEAGUE_KEY = "league";
    private static final String JSON_PLAYOFF_KEY = "playoff";


    private Stage stage;
    private Table levelContainer;

    public TournamentChoosingScreen(final Game game, final Screen previousScreen) {
        PagedScrollPane scrollPane = new PagedScrollPane();
        scrollPane.setFlingTime(FLING_TIME);

        final TournamentsReader reader = new TournamentsReader();

        for (int i = 0; i < reader.getTournamentsNumber(); i++) {
            Table tournamentCell = new Table();

            ImageButton tournamentImage = new ImageButton(AssetLoader.tournamentsSkin.getDrawable(reader.getTournamentIconName(i)));
            tournamentCell.add(tournamentImage).pad(Constants.UI_ELEMENTS_INDENT).row();
            final int index = i;
            tournamentImage.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (reader.getTournamentType(index).equals(JSON_LEAGUE_KEY)) {
                        dispose();
                        game.setScreen(new LeagueTournamentScreen(game, reader.getTournament(index)));
                    }
                    if (reader.getTournamentType(index).equals(JSON_PLAYOFF_KEY)) {
                        dispose();
                        Tournament tournament = reader.getTournament(index);
                        game.setScreen(new PlayOffTournamentScreen(game, tournament, tournament.getParticipants().get(0)));
                    }
                }
            });

            Label tournamentName = new Label(reader.getTournamentName(i), AssetLoader.gameLabelStyle);
            tournamentCell.add(tournamentName).pad(Constants.UI_ELEMENTS_INDENT).row();

            scrollPane.addPage(tournamentCell);
        }
        levelContainer = new Table();
        levelContainer.setFillParent(true);
        levelContainer.add(scrollPane).expand().fill();

        Stack stack = new Stack();
        stack.setFillParent(true);
        stack.addActor(levelContainer);
        stack.addActor(new BackButtonTable(game, this, previousScreen));

        stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));
        stage.addActor(stack);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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