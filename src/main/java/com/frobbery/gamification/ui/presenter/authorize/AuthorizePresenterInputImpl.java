package com.frobbery.gamification.ui.presenter.authorize;

import com.frobbery.gamification.dto.AuthorizeDto;
import com.frobbery.gamification.exception.UserNotFoundException;
import com.frobbery.gamification.ui.interactor.UIInteractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizePresenterInputImpl implements AuthorizePresenterInput {

    private final UIInteractor interactor;

    private AuthorizePresenterOutput authorizePresenterOutput;

    public void tryAuthorize(AuthorizeDto authorizeDto) {
        try {
            var authorizationComplete = interactor.authorizeUser(authorizeDto);
            if (authorizationComplete)  {
                authorizePresenterOutput.goToLevelsPage();
            } else {
                authorizePresenterOutput.showErrorDialog("Неверный пароль");
            }
        } catch (UserNotFoundException unfe) {
            authorizePresenterOutput.showErrorDialog(unfe.getMessage());
        } catch (Exception e) {
            authorizePresenterOutput.showErrorDialog("Произошла ошибка во время авторизации. Повторите попытку позже.");
        }
    }

    @Override
    public void setPresenterOutput(AuthorizePresenterOutput presenterOutput) {
        this.authorizePresenterOutput = presenterOutput;
    }
}
