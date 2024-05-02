package com.frobbery.gamification.ui.presenter.authorize;

import com.frobbery.gamification.ui.interactor.UIInteractor;
import com.frobbery.gamification.util.dto.AuthorizeDto;
import com.frobbery.gamification.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizePresenterInputImpl implements AuthorizePresenterInput {

    private final UIInteractor interactor;

    private AuthorizePresenterOutput authorizePresenterOutput;

    public void tryAuthorize(AuthorizeDto authorizeDto) {
        try {
            interactor.authorizeUser(authorizeDto);
            authorizePresenterOutput.goToLevelsPage();
        } catch (InternalAuthenticationServiceException iase) {
            if (iase.getCause() instanceof UserNotFoundException) {
                authorizePresenterOutput.showErrorDialog(iase.getMessage());
            } else {
                authorizePresenterOutput.showErrorDialog("Произошла ошибка во время авторизации. Повторите попытку позже.");
            }
        } catch (BadCredentialsException bce) {
            authorizePresenterOutput.showErrorDialog("Неверный пароль");
        }
    }

    @Override
    public void setPresenterOutput(AuthorizePresenterOutput presenterOutput) {
        this.authorizePresenterOutput = presenterOutput;
    }
}
