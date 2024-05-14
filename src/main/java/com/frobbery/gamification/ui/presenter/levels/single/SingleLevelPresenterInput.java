package com.frobbery.gamification.ui.presenter.levels.single;

import com.frobbery.gamification.ui.presenter.PresenterInput;
import com.frobbery.gamification.util.dto.LevelDto;
import org.springframework.security.core.Authentication;

public interface SingleLevelPresenterInput extends PresenterInput<SingleLevelPresenterOutput> {

    LevelDto getCurrentLevel(int levelNum);

    void checkCode(Authentication auth, int levelNumber, String initialCode, String code);
}
