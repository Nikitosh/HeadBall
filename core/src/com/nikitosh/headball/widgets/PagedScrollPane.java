package com.nikitosh.headball.widgets;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.utils.Constants;

public class PagedScrollPane extends ScrollPane {

    private Table content;
    private boolean wasPanDragFling = false;

    public PagedScrollPane() {
        super(null);
        setup();
    }

    private void setup() {
        content = new Table();
        content.defaults().space(Constants.UI_ELEMENTS_INDENT);
        super.setWidget(content);
    }

    public void addPage(Actor page) {
        content.add(page).expandY();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (wasPanDragFling && !isPanning() && !isDragging() && !isFlinging()) {
            wasPanDragFling = false;
            scrollToPage();
        }
        else {
            if (isPanning() || isDragging() || isFlinging()) {
                wasPanDragFling = true;
            }
        }
    }

    private void scrollToPage() {
        float scrollX = getScrollX();
        float maxX = getMaxX();

        if (scrollX >= maxX || scrollX <= 0) {
            return;
        }

        Array<Actor> pages = content.getChildren();
        float pageX = 0;
        float pageWidth;
        if (pages.size > 0) {
            for (Actor page : pages) {
                pageX = page.getX();
                pageWidth = page.getWidth();
                if (scrollX < (pageX + pageWidth * 0.5)) {
                    break;
                }
            }
            setScrollX(MathUtils.clamp(pageX, 0, maxX));
        }
    }

    public void setPageSpacing(float pageSpacing) {
        if (content != null) {
            content.defaults().space(pageSpacing);
            for (Cell cell : content.getCells()) {
                cell.space(pageSpacing);
            }
            content.invalidate();
        }
    }
}
