package com.frobbery.gamification.ui.presenter.levels;

import com.frobbery.gamification.ui.presenter.PresenterInput;
import org.springframework.security.core.Authentication;

public interface LevelsPresenterInput extends PresenterInput<LevelsPresenterOutput> {

    long getAvailableLevelsNum();

    int getLastOpenLevelNum(Authentication authentication);

    void goToLevel(int levelNum);

    void checkForTimedAchievements(Authentication authentication);
}
