package com.frobbery.gamification.ui.view;

import com.frobbery.gamification.ui.dialog.AchievementDialog;
import com.frobbery.gamification.ui.dialog.NotificationDialog;
import com.frobbery.gamification.util.dto.AchievementDto;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class ViewOutput extends VerticalLayout {

    protected void showErrorDialog(String errorText) {
        var dialog = new NotificationDialog(errorText);
        dialog.open();
    }

    protected void showConfirmationDialog(String notificationText, Runnable action) {
        var dialog = new NotificationDialog(notificationText, action);
        dialog.open();
    }

    protected void showAchievementDialog(AchievementDto achievement) {
        var dialog = new AchievementDialog(achievement);
        dialog.open();
    }
}
