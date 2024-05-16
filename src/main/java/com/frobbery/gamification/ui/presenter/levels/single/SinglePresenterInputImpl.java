package com.frobbery.gamification.ui.presenter.levels.single;

import com.frobbery.gamification.ui.interactor.UIInteractor;
import com.frobbery.gamification.util.dto.CheckCodeDto;
import com.frobbery.gamification.util.dto.LevelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class SinglePresenterInputImpl implements SingleLevelPresenterInput{

    private final UIInteractor interactor;

    private SingleLevelPresenterOutput presenterOutput;

    @Override
    public void setPresenterOutput(SingleLevelPresenterOutput presenterOutput) {
        this.presenterOutput = presenterOutput;
    }

    @Override
    public LevelDto getCurrentLevel(int levelNum) {
        return interactor.getLevelByNumber(levelNum);
    }

    @Override
    public void checkCode(Authentication auth, int levelNumber, String initialCode, String code) {
        var checkCodeDto = CheckCodeDto.builder()
                .levelNumber(levelNumber)
                .initialCode(initialCode)
                .code(code)
                .build();
        boolean checkResult = interactor.checkCode(checkCodeDto);
        if (checkResult) {
            boolean isLastLevel = interactor.isLastLevel(levelNumber);
            if (nonNull(auth) && !(auth instanceof AnonymousAuthenticationToken)) {
                var userEmail = ((UserDetails) auth.getPrincipal()).getUsername();
                var achievement = interactor.addLevelAchievementToUser(userEmail, levelNumber, isLastLevel);
                presenterOutput.showSuccessDialog(isLastLevel);
                if (nonNull(achievement)) {
                    presenterOutput.showAchievementDialog(achievement);
                }
            } else {
                presenterOutput.showSuccessDialog(isLastLevel);
            }
        } else {
            presenterOutput.showFailureDialog();
        }
    }
}
