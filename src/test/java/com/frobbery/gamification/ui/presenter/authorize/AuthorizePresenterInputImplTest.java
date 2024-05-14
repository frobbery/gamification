package com.frobbery.gamification.ui.presenter.authorize;

import com.frobbery.gamification.service.user.authentication.exception.UserNotFoundException;
import com.frobbery.gamification.ui.interactor.UIInteractor;
import com.frobbery.gamification.util.dto.AuthorizeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorizePresenterInputImplTest {

    @Mock
    private UIInteractor interactor;

    @Mock
    private AuthorizePresenterOutput presenterOutput;

    @InjectMocks
    private AuthorizePresenterInputImpl sut;

    @BeforeEach
    void setPresenterOutput() {
        sut.setPresenterOutput(presenterOutput);
    }

    @Test
    void shouldAuthorizeUserSuccess() {
        //given
        var authorizeDto = mock(AuthorizeDto.class);

        //when
        sut.tryAuthorize(authorizeDto);

        //then
        verify(interactor, times(1)).authorizeUser(authorizeDto);
        verify(presenterOutput, times(1)).goToLevelsPage();
    }

    @SuppressWarnings("all")
    @Test
    void shouldShowErrorDialog_whenUserNotFound() {
        //given
        var authorizeDto = mock(AuthorizeDto.class);
        var message = "userNotFoundMessage";
        doThrow(new InternalAuthenticationServiceException(message).initCause(new UserNotFoundException(""))).when(interactor)
                .authorizeUser(authorizeDto);

        //when
        sut.tryAuthorize(authorizeDto);

        //then
        verify(presenterOutput, times(1)).showErrorDialog(message);
    }

    @Test
    void shouldShowErrorDialog_whenUnknownExpression() {
        //given
        var authorizeDto = mock(AuthorizeDto.class);
        doThrow(new InternalAuthenticationServiceException("")).when(interactor)
                .authorizeUser(authorizeDto);

        //when
        sut.tryAuthorize(authorizeDto);

        //then
        verify(presenterOutput, times(1)).showErrorDialog("Произошла ошибка во время авторизации." +
                " Повторите попытку позже.");
    }

    @Test
    void shouldShowErrorDialog_whenBadCredentials() {
        //given
        var authorizeDto = mock(AuthorizeDto.class);
        doThrow(new BadCredentialsException("")).when(interactor)
                .authorizeUser(authorizeDto);

        //when
        sut.tryAuthorize(authorizeDto);

        //then
        verify(presenterOutput, times(1)).showErrorDialog("Неверный пароль");
    }
}