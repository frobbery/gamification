package com.frobbery.gamification.ui.view;

import com.frobbery.gamification.ui.presenter.levels.single.SingleLevelPresenterInput;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.frobbery.gamification.ui.util.UiUtils.createCodeArea;
import static com.frobbery.gamification.ui.util.UiUtils.createDefaultAnimationArea;
import static com.frobbery.gamification.ui.util.UiUtils.createGoBackButton;
import static com.frobbery.gamification.ui.util.UiUtils.createLogOutButton;
import static com.frobbery.gamification.ui.util.UiUtils.createPlayButton;
import static com.frobbery.gamification.ui.util.UiUtils.createRefreshButton;
import static com.frobbery.gamification.ui.util.UiUtils.setDefaultLayoutStyle;

@Route(value = "levels/level-1")
@CssImport(value = "./styles/style.css")
public class SingleLevelView extends VerticalLayout {

    private final SingleLevelPresenterInput presenterInput;
    private final Button playButton = createPlayButton();
    private final Button refreshButton = createRefreshButton();
    private final boolean authenticated;

    public SingleLevelView(SingleLevelPresenterInput presenterInput) {
        this.presenterInput = presenterInput;
        authenticated = !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
        init();
    }

    private void init() {
        setDefaultLayoutStyle(this);
        initTopLayout();
        initLevelLayout();
    }

    private void initTopLayout() {
        var goBackButton = createGoBackButton();
        if (authenticated) {
            goBackButton.addClickListener(event -> UI.getCurrent().navigate("levels"));
        } else {
            goBackButton.addClickListener(event -> UI.getCurrent().navigate(""));
        }
        var logOutButton = createLogOutButton();
        logOutButton.setVisible(authenticated);
        logOutButton.addClickListener(event -> UI.getCurrent().navigate("levels"));
        add(goBackButton, logOutButton);
    }

    private void initLevelLayout() {
        var taskLayout = new VerticalLayout();
        var topTaskBlock = new HorizontalLayout();
        taskLayout.setWidth("500px");
        //var taskLabel =
        var codeArea = createCodeArea("//Initial code");
        var animationArea = new VerticalLayout(createDefaultAnimationArea());
        var levelLayout = new HorizontalLayout(codeArea, animationArea);
        levelLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        levelLayout.setWidthFull();
        add(levelLayout);
    }
}
