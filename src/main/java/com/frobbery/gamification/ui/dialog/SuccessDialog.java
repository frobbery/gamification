package com.frobbery.gamification.ui.dialog;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.frobbery.gamification.ui.util.UiUtils.createDarkButton;
import static com.frobbery.gamification.ui.util.UiUtils.createLightButton;
import static com.frobbery.gamification.ui.util.UiUtils.createSmallHeadLineLayout;

public class SuccessDialog extends Dialog {

    private final Authentication authentication;

    private final Button goToMainPageButton = createLightButton("На главную");

    private final Button retryButton = createDarkButton("Повторить еще раз");

    private final Button nextLevelButton = createDarkButton("Следующий уровень");

    private final Runnable goToNextLevelAction;

    public SuccessDialog(Runnable goToNextLevelAction, String successMessage, boolean isLastLevel) {
        this.goToNextLevelAction = goToNextLevelAction;
        authentication = SecurityContextHolder.getContext().getAuthentication();
        setWidth("600px");
        init(successMessage, isLastLevel);
        enableButtonListeners();
    }

    private void init(String successMessage, boolean isLastLevel) {
        addClassName("dialog");
        var dialogLayout = new VerticalLayout();
        dialogLayout.setHeightFull();
        dialogLayout.setWidthFull();
        dialogLayout.add(createSmallHeadLineLayout("Ура!"));

        var label = new NativeLabel(successMessage);
        label.setWidthFull();
        label.addClassName("common-text-with-padding");

        if (!isLastLevel) {
            var buttonLayout = new HorizontalLayout(retryButton, nextLevelButton);
            buttonLayout.setWidthFull();
            buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            buttonLayout.getStyle().set("padding-bottom", "10px");
            goToMainPageButton.addClassName("bottom-position");
            dialogLayout.add(label, buttonLayout, goToMainPageButton);
        } else {
            var buttonLayout = new HorizontalLayout(goToMainPageButton, retryButton);
            buttonLayout.setWidthFull();
            buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            buttonLayout.addClassName("bottom-position");
            dialogLayout.add(label, buttonLayout);
        }
        add(dialogLayout);
    }

    private void enableButtonListeners() {
        nextLevelButton.addClickListener(event -> {
            close();
            goToNextLevelAction.run();
        });
        retryButton.addClickListener(event -> close());
        goToMainPageButton.addClickListener(event -> {
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                UI.getCurrent().navigate("levels");
                close();
            } else {
                UI.getCurrent().navigate("");
                close();
            }
        });
    }
}
