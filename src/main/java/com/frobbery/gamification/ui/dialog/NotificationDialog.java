package com.frobbery.gamification.ui.dialog;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;



import static com.frobbery.gamification.ui.util.UiUtils.createDarkButton;

@CssImport(value = "./styles/style.css", themeFor = "vaadin-dialog-overlay")
public class NotificationDialog extends Dialog {

    private final Button okButton = createDarkButton("Ок");

    public NotificationDialog(String notificationText) {
        setWidth("400px");
        init(notificationText);
        okButton.addClickListener(event -> close());
    }

    public NotificationDialog(String notificationText, Runnable action) {
        init(notificationText);
        okButton.addClickListener(event -> {
            action.run();
            close();
        });
    }

    private void init(String notificationText) {
        addClassName("dialog");
        var dialogLayout = new VerticalLayout();
        dialogLayout.setHeightFull();
        dialogLayout.setWidthFull();

        var label = new NativeLabel(notificationText);
        label.setWidthFull();
        label.addClassName("common-text");

        okButton.addClassName("ok-button");

        dialogLayout.add(label, okButton);
        add(dialogLayout);
    }
}
