package com.frobbery.gamification.ui.presenter.levels;

import com.frobbery.gamification.ui.presenter.PresenterOutput;
import com.frobbery.gamification.util.dto.AchievementDto;

public interface LevelsPresenterOutput extends PresenterOutput {
    void goToLevel(int levelNum);

    void showAchievementDialog(AchievementDto achievement);
}
