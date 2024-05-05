package com.frobbery.gamification.ui.view;

import com.frobbery.gamification.ui.presenter.levels.LevelsPresenterInput;
import com.frobbery.gamification.ui.presenter.levels.LevelsPresenterOutput;
import com.frobbery.gamification.util.dto.AchievementDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.MessageFormat;

import static com.frobbery.gamification.ui.util.UiUtils.createDarkButton;
import static com.frobbery.gamification.ui.util.UiUtils.createDarkLevelButton;
import static com.frobbery.gamification.ui.util.UiUtils.createDisabledLevelButton;
import static com.frobbery.gamification.ui.util.UiUtils.createHeadLineLayout;
import static com.frobbery.gamification.ui.util.UiUtils.createLightButton;
import static com.frobbery.gamification.ui.util.UiUtils.createLogOutButton;
import static com.frobbery.gamification.ui.util.UiUtils.setDefaultLayoutStyle;
import static com.frobbery.gamification.ui.util.UiUtils.setTopRightLayoutStyle;

@Route(value = "levels")
@CssImport(value = "./styles/style.css")
public class LevelsView extends ViewOutput implements LevelsPresenterOutput {

    private final LevelsPresenterInput presenterInput;

    private final Button goToPersonalCabinetButton = createDarkButton("Личный кабинет");

    private final Button logOutButton = createLogOutButton();

    private final Button continueButton = createLightButton("Продолжить игру");

    private final Authentication authentication;


    public LevelsView(LevelsPresenterInput presenterInput) {
        this.presenterInput = presenterInput;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        presenterInput.setPresenterOutput(this);
        init();
        presenterInput.checkForTimedAchievements(authentication);
    }

    private void init() {
        setDefaultLayoutStyle(this);
        initTopButtonLayout();
        initHeadLine();
        initLevelsButtons();
        enableButtonListeners();
    }

    private void initTopButtonLayout() {
        var topButtonLayout = new HorizontalLayout(goToPersonalCabinetButton, logOutButton);
        setTopRightLayoutStyle(topButtonLayout);
        add(topButtonLayout);
    }

    private void initHeadLine() {
        add(createHeadLineLayout("Продолжи игру"));
        add(continueButton);
        add(createHeadLineLayout("или выбери уровень для прохождения"));
    }

    private void initLevelsButtons() {
        var levelsNum = presenterInput.getAvailableLevelsNum();
        var lastAvailableLevel = presenterInput.getLastOpenLevelNum(authentication);
        var layoutNum = Math.ceil(levelsNum / 8.00);
        var allLevelsLayout = new VerticalLayout();
        allLevelsLayout.setWidthFull();
        for (int i = 0; i < layoutNum; i++) {
            var currentLevelsLayout = new HorizontalLayout();
            currentLevelsLayout.setWidthFull();
            currentLevelsLayout.setJustifyContentMode(JustifyContentMode.CENTER);
            int currentLevelsNum = 8;
            if (i == layoutNum - 1) {
                currentLevelsNum = (levelsNum - i * 8) % 8;
            }
            for (int j = 0; j < (currentLevelsNum > 0 ? currentLevelsNum : 8); j++) {
                int levelNum = i * 8 + j + 1;
                if (levelNum <= lastAvailableLevel) {
                    var button = createDarkLevelButton(levelNum);
                    button.addClickListener(event -> presenterInput.goToLevel(levelNum));
                    currentLevelsLayout.add(button);
                } else {
                    currentLevelsLayout.add(createDisabledLevelButton(levelNum));
                }
            }
            allLevelsLayout.add(currentLevelsLayout);
        }
        add(allLevelsLayout);
    }

    private void enableButtonListeners() {
        goToPersonalCabinetButton.addClickListener(event -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UI.getCurrent().navigate("personal-cabinet");
        });
        logOutButton.addClickListener(event -> {
            SecurityContextHolder.getContext().setAuthentication(null);
            UI.getCurrent().navigate("");
        });
    }

    @Override
    public void goToLevel(int levelNum) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UI.getCurrent().navigate(MessageFormat.format("levels/{0}", levelNum));
    }

    @Override
    public void showAchievementDialog(AchievementDto achievement) {
        super.showAchievementDialog(achievement);
    }
}
