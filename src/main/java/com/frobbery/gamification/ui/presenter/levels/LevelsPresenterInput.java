package com.frobbery.gamification.ui.presenter.levels;

import com.frobbery.gamification.ui.presenter.PresenterInput;

public interface LevelsPresenterInput extends PresenterInput<LevelsPresenterOutput> {

    int getAvailableLevelsNum();

    int getLastOpenLevelNum(String userEmail);

    void goToLevel(int levelNum);
}
