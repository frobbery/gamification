package com.frobbery.gamification.ui.presenter.registry;

import com.frobbery.gamification.ui.presenter.PresenterOutput;

public interface RegistryPresenterOutput extends PresenterOutput {
    void showErrorDialog(String errorText);

    void showNotificationDialog(String notificationText, Runnable action);

    void goToMainPage();
}
