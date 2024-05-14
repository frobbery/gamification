package com.frobbery.gamification.ui.view;

import com.frobbery.gamification.ui.dialog.AchievementDescriptionDialog;
import com.frobbery.gamification.ui.presenter.personal_cabinet.PersonalCabinetPresenterInput;
import com.frobbery.gamification.ui.presenter.personal_cabinet.PersonalCabinetPresenterOutput;
import com.frobbery.gamification.util.dto.ReceivedAchievementDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.frobbery.gamification.ui.util.UiUtils.createAchievementLabel;
import static com.frobbery.gamification.ui.util.UiUtils.createAchievementLayout;
import static com.frobbery.gamification.ui.util.UiUtils.createGoBackButton;
import static com.frobbery.gamification.ui.util.UiUtils.createStarLayout;
import static com.frobbery.gamification.ui.util.UiUtils.createTopLogOutButton;
import static com.frobbery.gamification.ui.util.UiUtils.createTopRightHeadLineLayout;
import static com.frobbery.gamification.ui.util.UiUtils.setPersonalCabinetLayoutStyle;

@Route(value = "personal-cabinet")
@CssImport(value = "./styles/style.css")
public class PersonalCabinetView extends ViewOutput implements PersonalCabinetPresenterOutput {

    private final PersonalCabinetPresenterInput presenterInput;

    private final Authentication authentication;

    public PersonalCabinetView(PersonalCabinetPresenterInput presenterInput) {
        this.presenterInput = presenterInput;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        init();
    }

    private void init() {
        setPersonalCabinetLayoutStyle(this);
        initTopLayout();
        initHeadline();
        initAchievementsLayout();
    }

    private void initTopLayout() {
        var goBackButton = createGoBackButton();
        goBackButton.addClickListener(event -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UI.getCurrent().navigate("levels");
        });
        var logOutButton = createTopLogOutButton();
        logOutButton.addClickListener(event -> {
            SecurityContextHolder.getContext().setAuthentication(null);
            UI.getCurrent().navigate("");
        });
        add(goBackButton, logOutButton);
    }

    private void initHeadline() {
        add(createTopRightHeadLineLayout("Личный кабинет"));
    }

    private void initAchievementsLayout() {
        var label = createAchievementLabel();
        add(label);
        var achievementLayout = createAchievementLayout();
        var receivedAchievements = presenterInput.getReceivedAchievementOfUser(authentication);
        var layoutsNum = Math.ceil(receivedAchievements.size() / 7.0);
        for (int i = 0; i < layoutsNum; i++) {
            var currentLayout = new HorizontalLayout();
            currentLayout.setJustifyContentMode(JustifyContentMode.START);
            currentLayout.setVerticalComponentAlignment(Alignment.START);
            int currentAchievementNum = 7;
            if (i == layoutsNum - 1) {
                currentAchievementNum = (receivedAchievements.size() - i * 7) % 7;
            }
            for (int j = 0; j < (currentAchievementNum != 0 ? currentAchievementNum : 7); j++) {
                var currentAchievement = receivedAchievements.get(i * 7 + j);
                var starLayout = createStarLayout(currentAchievement.getName());
                starLayout.addClickListener(event -> showAchievementDescriptionDialog(currentAchievement));
                currentLayout.add(starLayout);
            }
            achievementLayout.add(currentLayout);
        }
        add(achievementLayout);
    }

    private void showAchievementDescriptionDialog(ReceivedAchievementDto receivedAchievement) {
        var dialog = new AchievementDescriptionDialog(receivedAchievement);
        dialog.open();
    }
}
