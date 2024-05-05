package com.frobbery.gamification.ui.presenter.levels.single;

import com.frobbery.gamification.ui.presenter.PresenterOutput;
import com.frobbery.gamification.util.dto.AchievementDto;

public interface SingleLevelPresenterOutput extends PresenterOutput {
    void showFailureDialog();

    void showSuccessDialog(boolean isLastLevel);

    void showAchievementDialog(AchievementDto achievement);
}
