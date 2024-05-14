package com.frobbery.gamification.ui.presenter.registry;

import com.frobbery.gamification.ui.interactor.UIInteractor;
import com.frobbery.gamification.util.dto.RegistryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistryPresenterInputImplTest {

    @Mock
    private UIInteractor interactor;

    @Mock
    private RegistryPresenterOutput presenterOutput;

    @InjectMocks
    private RegistryPresenterInputImpl sut;

    @BeforeEach
    void setPresenterOutput() {
        sut.setPresenterOutput(presenterOutput);
    }

    @Test
    void shouldShowErrorDialog_whenUserExists() {
        //given
        var registryDto = mock(RegistryDto.class);
        when(interactor.checkIfUserExists(registryDto)).thenReturn(true);

        //when
        sut.tryRegistry(registryDto);

        //then
        verify(presenterOutput, times(1)).showErrorDialog("Пользователь с таким email или никнеймом уже существует");
    }

    @Test
    void shouldShowNotificationDialog_whenUserNotExistsAndRegistrySuccessful() {
        //given
        var registryDto = mock(RegistryDto.class);
        when(interactor.checkIfUserExists(registryDto)).thenReturn(false);

        //when
        sut.tryRegistry(registryDto);

        //then
        ArgumentCaptor<Runnable> captorLambda = ArgumentCaptor.forClass(Runnable.class);
        verify(presenterOutput, times(1)).showNotificationDialog(eq("Регистрация прошла успешно"),
                captorLambda.capture());
        captorLambda.getValue().run();
        verify(presenterOutput, times(1)).goToMainPage();
    }

    @Test
    void shouldShowNotificationDialog_whenUserNotExistsAndRegistryFailed() {
        //given
        var registryDto = mock(RegistryDto.class);
        when(interactor.checkIfUserExists(registryDto)).thenReturn(false);
        doThrow(RuntimeException.class).when(interactor).registerUser(registryDto);

        //when
        sut.tryRegistry(registryDto);

        //then
        verify(presenterOutput, times(1)).showErrorDialog("Произошла ошибка во время регистрации." +
                " Повторите попытку позже.");
    }
}