package com.frobbery.gamification.ui.presenter.levels;

import com.frobbery.gamification.ui.interactor.UIInteractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LevelsPresenterInputImpl implements LevelsPresenterInput {

    private final UIInteractor interactor;

    private LevelsPresenterOutput presenterOutput;

    @Override
    public int getAvailableLevelsNum() {
        return interactor.getAvailableLevelsNum();
    }

    @Override
    public int getLastOpenLevelNum(String userEmail) {
        return interactor.getLastOpenLevelNum(userEmail);
    }

    @Override
    public void goToLevel(int levelNum) {
        //TODO add goingtolevel
        presenterOutput.goToLevel(levelNum);
    }

    @Override
    public void setPresenterOutput(LevelsPresenterOutput presenterOutput) {
        this.presenterOutput = presenterOutput;
    }
}
