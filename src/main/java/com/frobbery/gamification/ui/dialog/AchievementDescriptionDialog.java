package com.frobbery.gamification.ui.dialog;

import com.frobbery.gamification.util.dto.ReceivedAchievementDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.text.MessageFormat;

import static com.frobbery.gamification.ui.util.UiUtils.createAchievementReceivedLayout;
import static com.frobbery.gamification.ui.util.UiUtils.createDarkButton;
import static com.frobbery.gamification.ui.util.UiUtils.createSmallHeadLineLayout;

@CssImport(value = "./styles/style.css", themeFor = "vaadin-dialog-overlay")
public class AchievementDescriptionDialog extends Dialog {

    private final Button okButton = createDarkButton("Ура!");

    public AchievementDescriptionDialog(ReceivedAchievementDto achievement) {
        setWidth("600px");
        init(achievement);
        okButton.addClickListener(event -> close());
    }

    private void init(ReceivedAchievementDto achievement) {
        addClassName("dialog");
        var dialogLayout = new VerticalLayout();
        dialogLayout.setHeightFull();
        dialogLayout.setWidthFull();
        dialogLayout.setSpacing(false);
        dialogLayout.add(createSmallHeadLineLayout(MessageFormat.format("\"{0}\"", achievement.getName())));
        dialogLayout.add(createAchievementReceivedLayout(achievement.getReceivedDate()));

        var label = new NativeLabel(achievement.getDescription());
        label.setWidthFull();
        label.addClassName("common-text-with-padding");
        label.getStyle().set("font-size", "25px");

        okButton.addClassName("bottom-position");

        dialogLayout.add(label, okButton);
        add(dialogLayout);
    }
}
