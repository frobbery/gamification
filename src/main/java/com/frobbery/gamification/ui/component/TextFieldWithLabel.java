package com.frobbery.gamification.ui.component;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

@Getter
@CssImport(value = "./styles/style.css", themeFor = "vaadin-text-field")
public class TextFieldWithLabel extends HorizontalLayout {

    private final TextField textField;

    public TextFieldWithLabel(String labelText) {
        var label = new NativeLabel(labelText);
        label.addClassName("field-label");
        textField = new TextField();
        textField.addClassName("text-field");
        setSpacing(false);
        add(label, textField);
        setWidthFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        getStyle().set("padding-bottom", "20px");
    }

    public void setEmptyValue() {
        textField.setValue("");
    }
}
