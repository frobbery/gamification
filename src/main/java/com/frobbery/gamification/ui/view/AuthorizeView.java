package com.frobbery.gamification.ui.view;

import com.frobbery.gamification.ui.component.PasswordFieldWithLabel;
import com.frobbery.gamification.ui.component.TextFieldWithLabel;
import com.frobbery.gamification.ui.presenter.authorize.AuthorizePresenterInput;
import com.frobbery.gamification.ui.presenter.authorize.AuthorizePresenterInputImpl;
import com.frobbery.gamification.ui.presenter.authorize.AuthorizePresenterOutput;
import com.frobbery.gamification.util.dto.AuthorizeDto;
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

@Route(value = "authorize")
@CssImport(value = "./styles/style.css")
public class AuthorizeView extends ViewOutput implements AuthorizePresenterOutput {

    private final AuthorizePresenterInput authorizePresenter;

    private final Button goBackButton = createGoBackButton();

    private final Button loginButton = createDarkButton("Войти");

    private final Binder<AuthorizeDto> binder = new Binder<>();

    public AuthorizeView(AuthorizePresenterInputImpl authorizePresenter) {
        this.authorizePresenter = authorizePresenter;
        authorizePresenter.setPresenterOutput(this);
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
        setAlignSelf(Alignment.START, goBackButton);
        var headLine = createHeadLineLayout("Авторизация");
        add(headLine);
    }

    private void initBindFields() {
        binder.setBean(new AuthorizeDto());
        var email = new TextFieldWithLabel("E-Mail");
        binder.forField(email.getTextField())
                .withValidator(s -> s.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"),
                        "E-Mail неверного формата")
                .bind(AuthorizeDto::getEmail, AuthorizeDto::setEmail);
        var password = new PasswordFieldWithLabel("Пароль");
        binder.forField(password.getPasswordField())
                .withValidator(s -> s.length() > 6, "Длина пароля должна быть больше шести символов")
                .bind(AuthorizeDto::getPassword, AuthorizeDto::setPassword);
        var fieldLayout = new VerticalLayout(email, password);
        add(fieldLayout);
        add(loginButton);
    }

    private void enableButtonsListeners() {
        goBackButton.addClickListener(event -> goBackButton.getUI().ifPresent(ui -> ui.navigate("")));
        loginButton.addClickListener(event -> {
            binder.validate();
            if (binder.isValid()) {
                authorizePresenter.tryAuthorize(binder.getBean());
            }
        });
    }

    @Override
    public void showErrorDialog(String errorText) {
        super.showErrorDialog(errorText);
    }

    @Override
    public void goToLevelsPage() {
        UI.getCurrent().navigate("levels");
    }
}
