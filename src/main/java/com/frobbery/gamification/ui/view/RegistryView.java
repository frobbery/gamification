package com.frobbery.gamification.ui.view;

import com.frobbery.gamification.ui.component.PasswordFieldWithLabel;
import com.frobbery.gamification.ui.component.TextFieldWithLabel;
import com.frobbery.gamification.ui.presenter.registry.RegistryPresenterInput;
import com.frobbery.gamification.ui.presenter.registry.RegistryPresenterOutput;
import com.frobbery.gamification.util.dto.RegistryDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import static com.frobbery.gamification.ui.util.UiUtils.createDarkButton;
import static com.frobbery.gamification.ui.util.UiUtils.createGoBackButton;
import static com.frobbery.gamification.ui.util.UiUtils.createHeadLineLayout;
import static com.frobbery.gamification.ui.util.UiUtils.setDefaultLayoutStyle;

@Route(value = "registry")
@CssImport(value = "./styles/style.css")
public class RegistryView extends ViewOutput implements RegistryPresenterOutput {

    private final RegistryPresenterInput registryPresenter;

    private final Button goBackButton = createGoBackButton();

    private final TextFieldWithLabel email = new TextFieldWithLabel("E-Mail");

    private final TextFieldWithLabel nickName = new TextFieldWithLabel("Никнейм");

    private final PasswordFieldWithLabel password = new PasswordFieldWithLabel("Пароль");

    private final Button registryButton = createDarkButton("Зарегистрироваться");

    private final Binder<RegistryDto> binder = new Binder<>();

    public RegistryView(RegistryPresenterInput registryPresenter) {
        this.registryPresenter = registryPresenter;
        registryPresenter.setPresenterOutput(this);
        init();
    }

    private void init() {
        setDefaultLayoutStyle(this);
        initHeadLine();
        initBindFields();
        enableButtonsListeners();
    }

    private void initHeadLine() {
        add(goBackButton);
        var headLine = createHeadLineLayout("Регистрация");
        add(headLine);
    }

    private void initBindFields() {
        binder.setBean(new RegistryDto());
        binder.forField(email.getTextField())
                .withValidator(s -> s.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"),
                        "E-Mail неверного формата")
                .bind(RegistryDto::getEmail, RegistryDto::setEmail);
        binder.forField(nickName.getTextField())
                .withValidator(s -> s.length() > 5, "Длина никнейма должна быть больше пяти символов")
                .bind(RegistryDto::getNickName, RegistryDto::setNickName);
        binder.forField(password.getPasswordField())
                .withValidator(s -> s.length() > 6, "Длина пароля должна быть больше шести символов")
                .bind(RegistryDto::getPassword, RegistryDto::setPassword);
        var fieldLayout = new VerticalLayout(email, nickName, password);
        add(fieldLayout);
        add(registryButton);
    }

    private void enableButtonsListeners() {
        goBackButton.addClickListener(event -> goToMainPage());
        registryButton.addClickListener(event -> {
            binder.validate();
            if (binder.isValid()) {
                registryPresenter.tryRegistry(binder.getBean());
            }
        });
    }

    @Override
    public void showErrorDialog(String errorText) {
        super.showErrorDialog(errorText);
    }

    @Override
    public void showNotificationDialog(String notificationText, Runnable action) {
        clearInput();
        super.showConfirmationDialog(notificationText, action);
    }

    private void clearInput() {
        binder.setValidatorsDisabled(true);
        email.setEmptyValue();
        nickName.setEmptyValue();
        password.setEmptyValue();
        binder.setValidatorsDisabled(false);
    }

    @Override
    public void goToMainPage() {
        UI.getCurrent().navigate("");
    }
}
