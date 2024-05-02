package com.frobbery.gamification.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import static com.frobbery.gamification.ui.util.UiUtils.createDarkButton;
import static com.frobbery.gamification.ui.util.UiUtils.createHeadLineLayout;
import static com.frobbery.gamification.ui.util.UiUtils.createLightButton;
import static com.frobbery.gamification.ui.util.UiUtils.setDefaultLayoutStyle;

@Route
@CssImport(value = "./styles/style.css")
public class MainView extends VerticalLayout {
    private final Button registryButton = createDarkButton("Зарегистрироваться");

    private final Button authorizeButton = createDarkButton("Авторизоваться");

    private final Button continueButton = createLightButton("Продолжить без регистрации");

    public MainView() {
        init();
    }

    private void init() {
        setDefaultLayoutStyle(this);
        initHeadLine();
        initTextArea();
        initButtons();
        enableButtonsListeners();
    }

    private void initHeadLine() {
        var headLine = createHeadLineLayout("ОБУЧИСЬ ПРОГРАММИРОВАНИЮ, ИГРАЯ!");
        add(headLine);
    }

    private void initTextArea() {
        var textArea = new NativeLabel("Для возможности сохранять уровни и достижения, необходимо войти");
        textArea.addClassName("common-text-area");
        textArea.setWidth("400px");
        textArea.setHeight("100px");
        add(textArea);
    }

    private void initButtons() {
        var buttonLayout = new HorizontalLayout(registryButton, authorizeButton);
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        add(buttonLayout);
        add(continueButton);
    }

    private void enableButtonsListeners() {
        registryButton.addClickListener(event -> registryButton.getUI().ifPresent(ui -> ui.navigate(RegistryView.class)));
        authorizeButton.addClickListener(event -> authorizeButton.getUI().ifPresent(ui -> ui.navigate(AuthorizeView.class)));
        continueButton.addClickListener(event -> continueButton.getUI().ifPresent(ui -> ui.navigate(LevelsView.class)));
    }
}
