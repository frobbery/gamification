package com.frobbery.gamification.ui.presenter;

public interface PresenterInput<O extends PresenterOutput> {
    void setPresenterOutput(O presenterOutput);
}
