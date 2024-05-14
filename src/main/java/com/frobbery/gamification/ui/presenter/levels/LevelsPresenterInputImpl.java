package com.frobbery.gamification.ui.presenter.levels;

import com.frobbery.gamification.ui.interactor.UIInteractor;
import com.frobbery.gamification.util.dto.AchievementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LevelsPresenterInputImpl implements LevelsPresenterInput {

    private final UIInteractor interactor;

    private LevelsPresenterOutput presenterOutput;

    @Override
    public long getAvailableLevelsNum() {
        return interactor.getAvailableLevelsNum();
    }

    @Override
    public int getLastOpenLevelNum(Authentication authentication) {
        var userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        return interactor.getLastOpenLevelNum(userEmail);
    }

    @Override
    public void goToLevel(int levelNum) {
        presenterOutput.goToLevel(levelNum);
    }

    @Override
    public void checkForTimedAchievements(Authentication authentication) {
        var userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        List<AchievementDto> newAchievements = interactor.getNewTimeAchievementsOfUser(userEmail);
        for (AchievementDto newAchievement: newAchievements) {
            presenterOutput.showAchievementDialog(newAchievement);
        }
    }

    @Override
    public void setPresenterOutput(LevelsPresenterOutput presenterOutput) {
        this.presenterOutput = presenterOutput;
    }
}
