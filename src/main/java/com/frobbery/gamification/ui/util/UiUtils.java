package com.frobbery.gamification.ui.util;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UiUtils {

    public static void setDefaultLayoutStyle(VerticalLayout layout) {
        layout.setWidthFull();
        layout.setHeightFull();
        layout.getStyle().set("background-color", "#FFF7AC");
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
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
