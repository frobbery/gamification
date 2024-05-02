package com.frobbery.gamification.ui.presenter.levels.single;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SinglePresenterInputImpl implements SingleLevelPresenterInput{

    private SingleLevelPresenterOutput presenterOutput;

    @Override
    public void setPresenterOutput(SingleLevelPresenterOutput presenterOutput) {
        this.presenterOutput = presenterOutput;
    }
}
