package com.frobbery.gamification.ui.view;

import com.frobbery.gamification.ui.dialog.FailureDialog;
import com.frobbery.gamification.ui.dialog.SuccessDialog;
import com.frobbery.gamification.ui.presenter.levels.single.SingleLevelPresenterInput;
import com.frobbery.gamification.ui.presenter.levels.single.SingleLevelPresenterOutput;
import com.frobbery.gamification.util.dto.AchievementDto;
import com.frobbery.gamification.util.dto.LevelDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.NavigationTrigger;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.MessageFormat;

import static com.frobbery.gamification.ui.util.UiUtils.createCodeArea;
import static com.frobbery.gamification.ui.util.UiUtils.createDefaultAnimationArea;
import static com.frobbery.gamification.ui.util.UiUtils.createGoBackButton;
import static com.frobbery.gamification.ui.util.UiUtils.createLevelDescriptionLayout;
import static com.frobbery.gamification.ui.util.UiUtils.createLevelLabelLayout;
import static com.frobbery.gamification.ui.util.UiUtils.createPlayButton;
import static com.frobbery.gamification.ui.util.UiUtils.createRefreshButton;
import static com.frobbery.gamification.ui.util.UiUtils.createTopLogOutButton;
import static com.frobbery.gamification.ui.util.UiUtils.createTopRightHeadLineLayout;
import static com.frobbery.gamification.ui.util.UiUtils.setCodeLayoutStyle;
import static com.frobbery.gamification.ui.util.UiUtils.setDefaultLayoutStyle;
import static com.frobbery.gamification.ui.util.UiUtils.setLevelLayoutStyle;

@Route(value = "levels/:levelNum")
@CssImport(value = "./styles/style.css")
@Scope(value = "prototype")
public class SingleLevelView extends ViewOutput implements BeforeEnterObserver, SingleLevelPresenterOutput {
    private final SingleLevelPresenterInput presenterInput;
    private Authentication authentication;
    private LevelDto currentLevel;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!event.getTrigger().equals(NavigationTrigger.UI_NAVIGATE)) {
            redirectToMainPage();
            event.rerouteTo("");
        } else {
            int levelNum = Integer.parseInt(event.getRouteParameters().get("levelNum").orElse("1"));
            this.currentLevel = presenterInput.getCurrentLevel(levelNum);
            init();
        }
    }

    public SingleLevelView(SingleLevelPresenterInput presenterInput) {
        this.presenterInput = presenterInput;
        presenterInput.setPresenterOutput(this);
    }

    private void init() {
        removeAll();
        setDefaultLayoutStyle(this);
        initTopLayout();
        initHeadline();
        initLevelLayout();
    }

    private void initTopLayout() {
        var goBackButton = createGoBackButton();
        goBackButton.addClickListener(event -> redirectToMainPage());
        var logOutButton = createTopLogOutButton();
        logOutButton.setVisible(isAuthenticated());
        logOutButton.addClickListener(event -> {
            SecurityContextHolder.getContext().setAuthentication(null);
            UI.getCurrent().navigate("");
        });
        add(goBackButton, logOutButton);
    }

    private void initHeadline() {
        add(createTopRightHeadLineLayout(MessageFormat.format("Уровень {0}", currentLevel.getNumber())));
    }

    private void initLevelLayout() {
        var codeLayout = getCodeLayout();
        var animationArea = createDefaultAnimationArea();
        var levelLayout = new HorizontalLayout(codeLayout, animationArea);
        setLevelLayoutStyle(levelLayout);
        add(levelLayout);
    }

    private VerticalLayout getCodeLayout() {
        Button playButton = createPlayButton();
        Button refreshButton = createRefreshButton();
        TextArea codeArea = createCodeArea();
        refreshButton.addClickListener(event -> codeArea.setValue(currentLevel.getInitialCode()));
        playButton.addClickListener(event -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            presenterInput.checkCode(authentication, currentLevel.getNumber(), currentLevel.getInitialCode(), codeArea.getValue());
        });
        var codeLayout = new VerticalLayout();
        setCodeLayoutStyle(codeLayout);
        codeLayout.add(createLevelLabelLayout(playButton, refreshButton));
        codeLayout.add(createLevelDescriptionLayout(currentLevel.getDescription(), currentLevel.getHint()));
        codeArea.setValue(currentLevel.getInitialCode());
        codeLayout.add(codeArea);
        codeLayout.setHeight("472px");
        return codeLayout;
    }

    private boolean isAuthenticated() {
        return !(authentication instanceof AnonymousAuthenticationToken);
    }

    @Override
    public void showFailureDialog() {
        var dialog = new FailureDialog();
        dialog.open();
    }

    @Override
    public void showSuccessDialog(boolean isLastLevel) {
        var dialog = new SuccessDialog(this::goToNextLevel, currentLevel.getSuccessMessage(), isLastLevel);
        dialog.open();
    }

    @Override
    public void showAchievementDialog(AchievementDto achievementDto) {
        super.showAchievementDialog(achievementDto);
    }

    private void goToNextLevel() {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UI.getCurrent().navigate(MessageFormat.format("levels/{0}", currentLevel.getNumber() + 1));
    }

    private void redirectToMainPage() {
        if (isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UI.getCurrent().navigate("levels");
        } else {
            UI.getCurrent().navigate("");
        }
    }
}
