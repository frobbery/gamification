package com.frobbery.gamification.ui.dialog;

import com.frobbery.gamification.util.dto.AchievementDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.text.MessageFormat;

import static com.frobbery.gamification.ui.util.UiUtils.createDarkButton;
import static com.frobbery.gamification.ui.util.UiUtils.createSmallHeadLineLayout;
import static com.frobbery.gamification.ui.util.UiUtils.createStarIcon;

@CssImport(value = "./styles/style.css", themeFor = "vaadin-dialog-overlay")
public class AchievementDialog extends Dialog {

    private final Button okButton = createDarkButton("Ура!");

    public AchievementDialog(AchievementDto achievement) {
        setWidth("400px");
        init(achievement);
        okButton.addClickListener(event -> close());
    }

    private void init(AchievementDto achievement) {
        addClassName("dialog");
        var dialogLayout = new VerticalLayout();
        dialogLayout.setHeightFull();
        dialogLayout.setWidthFull();
        dialogLayout.setSpacing(false);
        dialogLayout.add(createSmallHeadLineLayout("Ты получил достижение!"));

        var star = createStarIcon();

        var label = new NativeLabel(MessageFormat.format("\"{0}\"",achievement.getName()));
        label.setWidthFull();
        label.addClassName("common-text-with-padding");

        okButton.addClassName("bottom-position");

        dialogLayout.add(star, label, okButton);
        add(dialogLayout);
    }
}
