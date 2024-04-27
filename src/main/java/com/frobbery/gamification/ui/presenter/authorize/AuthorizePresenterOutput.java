package com.frobbery.gamification.ui.presenter.authorize;

import com.frobbery.gamification.ui.presenter.PresenterOutput;

public interface AuthorizePresenterOutput extends PresenterOutput {

    void showErrorDialog(String errorText);

    void goToLevelsPage();

}
