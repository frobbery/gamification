package com.frobbery.gamification.ui.view;

import com.frobbery.gamification.ui.presenter.levels.LevelsPresenterInput;
import com.frobbery.gamification.ui.presenter.levels.LevelsPresenterOutput;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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
public class LevelsView extends VerticalLayout implements LevelsPresenterOutput {

    private final LevelsPresenterInput presenterInput;

    private final Button goToPersonalCabinetButton = createDarkButton("Личный кабинет");

    private final Button logOutButton = createLogOutButton();

    private final Button continueButton = createLightButton("Продолжить игру");


    public LevelsView(LevelsPresenterInput presenterInput) {
        this.presenterInput = presenterInput;
        presenterInput.setPresenterOutput(this);
        init();
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
        var userEmail = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        var levelsNum = presenterInput.getAvailableLevelsNum();
        var lastAvailableLevel = presenterInput.getLastOpenLevelNum(userEmail);
        var layoutNum = Math.ceil(levelsNum / 8.00);
        var allLevelsLayout = new VerticalLayout();
        allLevelsLayout.setWidthFull();
        for (int i = 0; i < layoutNum; i++) {
            var currentLevelsLayout = new HorizontalLayout();
            currentLevelsLayout.setWidthFull();
            currentLevelsLayout.setJustifyContentMode(JustifyContentMode.CENTER);
            int currentLevelsNum = (levelsNum - i * 8) % 8;
            for (int j = 0; j < currentLevelsNum; j++) {
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
        //TODO добавить листенеры
    }

    @Override
    public void goToLevel(int levelNum) {
        UI.getCurrent().navigate("levels/level-1");
    }
}
