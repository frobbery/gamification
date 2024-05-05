package com.frobbery.gamification.ui.util;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import lombok.experimental.UtilityClass;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class UiUtils {

    public static void setDefaultLayoutStyle(VerticalLayout layout) {
        layout.setWidthFull();
        layout.setHeightFull();
        layout.getStyle().set("background-color", "#FFF7AC");
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
    }

    public static void setPersonalCabinetLayoutStyle(VerticalLayout layout) {
        layout.setWidthFull();
        layout.setHeightFull();
        layout.getStyle().set("background-color", "#FFF7AC");
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        layout.setHorizontalComponentAlignment(FlexComponent.Alignment.START);
    }

    public static void setTopRightLayoutStyle(HorizontalLayout layout) {
        layout.setWidthFull();
        layout.addClassName("top-layout");
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
    }

    public static void setCodeLayoutStyle(VerticalLayout layout) {
        layout.setWidthFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        layout.setSpacing(false);
        layout.setMargin(false);
        layout.setPadding(false);
    }

    public static void setLevelLayoutStyle(HorizontalLayout layout) {
        layout.setPadding(false);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.AROUND);
        layout.setVerticalComponentAlignment(FlexComponent.Alignment.STRETCH);
        layout.setWidthFull();
        layout.getStyle().set("margin-top", "-10px")
                .set("padding-right", "50px")
                .set("padding-left", "50px");
    }

    public static NativeLabel createHeadLineLayout(String text) {
        var headLine = new NativeLabel(text);
        headLine.addClassName("headline-font");
        headLine.setWidth("90%");
        return headLine;
    }

    public static NativeLabel createTopRightHeadLineLayout(String text) {
        var headLine = new NativeLabel(text);
        headLine.addClassName("right-headline-font");
        return headLine;
    }

    public static NativeLabel createSmallHeadLineLayout(String text) {
        var headLine = new NativeLabel(text);
        headLine.addClassName("small-headline-font");
        headLine.setWidth("90%");
        return headLine;
    }

    public static Button createDarkButton(String buttonText) {
        var button = new Button(buttonText);
        button.addClassName("dark-label");
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
        button.addClassName("dark-label");
        return button;
    }

    public static Button createTopLogOutButton() {
        var button = createLogOutButton();
        button.addClassName("top-layout");
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

    public static TextArea createCodeArea() {
        var codeArea = new TextArea();
        codeArea.getStyle().set("resize", "none");
        codeArea.addClassName("code-area");
        codeArea.setHeightFull();
        return codeArea;
    }

    public static Button createDefaultAnimationArea() {
        var animationArea = new Button("*Анимация персонажа*");
        animationArea.addClassName("default-animation");
        return animationArea;
    }

    public static Button createPlayButton() {
        var icon = VaadinIcon.PLAY.create();
        icon.setSize("35px");
        icon.getStyle().set("margin-left", "2px");
        var button = new Button(icon);
        button.addClassName("circle-button");
        return button;
    }

    public static Button createRefreshButton() {
        var button = new Button(VaadinIcon.REFRESH.create());
        button.addClassName("circle-button");
        return button;
    }

    public static HorizontalLayout createLevelLabelLayout(Button playButton, Button refreshButton) {
        var labelLayout = new HorizontalLayout();
        var levelLabel = createDarkButton("Задание");
        levelLabel.setEnabled(false);
        labelLayout.add(levelLabel, playButton, refreshButton);
        labelLayout.addClassName("to-the-right");
        labelLayout.setSpacing(false);
        return labelLayout;
    }

    public static VerticalLayout createLevelDescriptionLayout(String levelDescription, String levelHint) {
        var levelMainText = new NativeLabel(levelDescription);
        levelMainText.addClassName("level-description");
        var levelHintText = new NativeLabel(levelHint);
        levelHintText.addClassName("level-hint");
        var levelTextLayout = new VerticalLayout(levelMainText, levelHintText);
        levelTextLayout.addClassName("text-area");
        levelTextLayout.setWidth("500px");
        levelTextLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        levelTextLayout.setPadding(false);
        levelTextLayout.setMargin(false);
        levelTextLayout.setSpacing(false);
        return levelTextLayout;
    }

    public static Button createAchievementLabel() {
        var achievementLabel = createDarkButton("Достижения");
        achievementLabel.setEnabled(false);
        achievementLabel.addClassName("to-the-right");
        return achievementLabel;
    }

    public static VerticalLayout createAchievementLayout() {
        var layout = new VerticalLayout();
        layout.addClassName("text-area");
        layout.addClassName("upper");
        layout.setWidthFull();
        return layout;
    }

    public static Icon createStarIcon() {
        var star = VaadinIcon.STAR.create();
        star.setColor("gold");
        star.setSize("60px");
        star.getStyle().set("align-self", "center");
        return star;
    }

    public static VerticalLayout createStarLayout(String achievementName) {
        var layout = new VerticalLayout();
        layout.setWidth("160px");
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        layout.add(createStarIcon());
        var caption = new NativeLabel(MessageFormat.format("\"{0}\"", achievementName));
        caption.addClassName("common-text-with-padding");
        layout.add(caption);
        return layout;
    }

    public static VerticalLayout createAchievementReceivedLayout(LocalDate receiveDate) {
        var layout = new VerticalLayout();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        var star = createStarIcon();
        star.setSize("150px");
        layout.add(star);
        var caption = new NativeLabel(MessageFormat.format("Достижение получено {0}",
                DateTimeFormatter.ofPattern("dd.MM.yyyy").format(receiveDate)));
        caption.addClassName("common-text-with-padding");
        layout.add(caption);
        layout.setSpacing(false);
        return layout;
    }
}
