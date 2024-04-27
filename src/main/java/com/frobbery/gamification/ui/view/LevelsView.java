package com.frobbery.gamification.ui.view;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import static com.frobbery.gamification.ui.util.UiUtils.setDefaultLayoutStyle;

@Route(value = "levels")
@CssImport(value = "./styles/style.css")
public class LevelsView extends VerticalLayout {

    public LevelsView() {
        init();
    }

    private void init() {
        setDefaultLayoutStyle(this);
        initHeadLine();
    }

    private void initHeadLine() {
        var headLine = new NativeLabel("Это уровни");
        headLine.addClassName("headline-font");
        headLine.setWidth("90%");
        add(headLine);
    }
}
