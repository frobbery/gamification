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

public class FailureDialog extends Dialog {

    private final Authentication authentication;

    private final Button goToMainPageButton = createLightButton("На главную");

    private final Button retryButton = createDarkButton("Повторить еще раз");

    public FailureDialog() {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        setWidth("600px");
        init();
        enableButtonListeners();
        open();
    }

    private void init() {
        addClassName("dialog");
        var dialogLayout = new VerticalLayout();
        dialogLayout.setHeightFull();
        dialogLayout.setWidthFull();
        dialogLayout.add(createSmallHeadLineLayout("Что-то пошло не так:("));

        var firstLabel = new NativeLabel("У тебя не получилось пройти уровень");
        firstLabel.setWidthFull();
        firstLabel.addClassName("common-text");

        var secondLabel = new NativeLabel("Попробуешь еще раз?");
        secondLabel.setWidthFull();
        secondLabel.addClassName("common-text-with-padding");

        var labelLayout = new VerticalLayout(firstLabel, secondLabel);
        labelLayout.setSpacing(false);
        labelLayout.setPadding(false);
        labelLayout.setWidthFull();
        labelLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        var buttonLayout = new HorizontalLayout(goToMainPageButton, retryButton);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        buttonLayout.addClassName("bottom-position");

        dialogLayout.add(labelLayout, buttonLayout);
        add(dialogLayout);
    }

    private void enableButtonListeners() {
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
