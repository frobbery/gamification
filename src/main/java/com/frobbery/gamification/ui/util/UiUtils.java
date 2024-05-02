package com.frobbery.gamification.ui.util;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import lombok.experimental.UtilityClass;

import java.text.MessageFormat;

@UtilityClass
public class UiUtils {

    public static void setDefaultLayoutStyle(VerticalLayout layout) {
        layout.setWidthFull();
        layout.setHeightFull();
        layout.getStyle().set("background-color", "#FFF7AC");
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
    }

    public static void setTopRightLayoutStyle(HorizontalLayout layout) {
        layout.setWidthFull();
        layout.addClassName("top-layout");
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
    }

    public static NativeLabel createHeadLineLayout(String text) {
        var headLine = new NativeLabel(text);
        headLine.addClassName("headline-font");
        headLine.setWidth("90%");
        return headLine;
    }

    public static Button createDarkButton(String buttonText) {
        var button = new Button(buttonText);
        button.addClassName("dark-button");
        return button;
    }

    public static Button createLightButton(String buttonText) {
        var button = new Button(buttonText);
        button.addClassName("light-button");
        return button;
    }

    public static Button createGoBackButton() {
        var arrowIcon = VaadinIcon.ARROW_LEFT.create();
        arrowIcon.getStyle().set("background-color", "#989365");
        var button = new Button(arrowIcon);
        button.addClassName("back-button");
        return button;
    }

    public static Button createLogOutButton() {
        var logOutIcon = VaadinIcon.SIGN_OUT.create();
        logOutIcon.getStyle().set("background-color", "#676342");
        var button = new Button(logOutIcon);
        button.addClassName("dark-button");
        return button;
    }

    public static Button createDarkLevelButton(int levelNum) {
        var button = new Button(MessageFormat.format("Уровень {0}", levelNum));
        button.addClassName("dark-level");
        return button;
    }

    public static Button createDisabledLevelButton(int levelNum) {
        var button = new Button(MessageFormat.format("Уровень {0}", levelNum));
        button.addClassName("disabled-level");
        return button;
    }

    public static TextArea createCodeArea(String initialCode) {
        var codeArea = new TextArea();
        codeArea.setValue(initialCode);
        codeArea.addClassName("code-area");
        return codeArea;
    }

    public static Button createDefaultAnimationArea() {
        var animationArea = new Button("*Анимация персонажа*");
        animationArea.addClassName("default-animation");
        return animationArea;
    }

    public static Button createPlayButton() {
        var button = new Button(VaadinIcon.PLAY.create());
        button.addClassName("circle-button");
        return button;
    }

    public static Button createRefreshButton() {
        var button = new Button(VaadinIcon.REFRESH.create());
        button.addClassName("circle-button");
        return button;
    }

    public static NativeLabel createTaskLabel() {
        var label = new NativeLabel("Задание");
        label.addClassName("dark-button");
        return label;
    }

    public static HorizontalLayout createTextFieldWithLabel(String labelText) {
        var layout = new HorizontalLayout();
        var label = new NativeLabel(labelText);
        var textField = new TextField();
        layout.add(label);
        layout.add(textField);
        return layout;
    }

    public static void enableButton(Button button, boolean enabled) {
        button.setEnabled(enabled);
        if (enabled) {
            button.removeClassName("disabled-button");
            button.addClassName("dark-button");
        } else {
            button.addClassName("disabled-button");
            button.removeClassName("dark-button");
        }
    }
}
