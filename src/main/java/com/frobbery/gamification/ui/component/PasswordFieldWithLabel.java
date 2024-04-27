package com.frobbery.gamification.ui.component;

import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.Getter;

@Getter
public class PasswordFieldWithLabel extends HorizontalLayout {

    private final PasswordField passwordField;

    public PasswordFieldWithLabel(String labelText) {
        var label = new NativeLabel(labelText);
        label.addClassName("field-label");
        passwordField = new PasswordField();
        passwordField.addClassName("text-field");
        passwordField.setRevealButtonVisible(true);
        setSpacing(false);
        add(label, passwordField);
        setWidthFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    }

    public void setEmptyValue() {
        passwordField.setValue("");
    }
}
