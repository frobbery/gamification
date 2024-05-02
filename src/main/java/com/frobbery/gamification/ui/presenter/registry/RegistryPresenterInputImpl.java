package com.frobbery.gamification.ui.presenter.registry;

import com.frobbery.gamification.util.dto.RegistryDto;
import com.frobbery.gamification.ui.interactor.UIInteractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistryPresenterInputImpl implements RegistryPresenterInput {

    private final UIInteractor interactor;

    private RegistryPresenterOutput registryPresenterOutput;


    public void tryRegistry(RegistryDto registryDto) {
        if (interactor.checkIfUserExists(registryDto)) {
            registryPresenterOutput.showErrorDialog("Пользователь с таким email или никнеймом уже существует");
        } else {
            try {
                interactor.registerUser(registryDto);
                registryPresenterOutput.showNotificationDialog("Регистрация прошла успешно",
                        () -> registryPresenterOutput.goToMainPage());
            } catch (Exception e) {
                registryPresenterOutput.showErrorDialog("Произошла ошибка во время регистрации. Повторите попытку позже.");
            }
        }
    }

    @Override
    public void setPresenterOutput(RegistryPresenterOutput presenterOutput) {
        this.registryPresenterOutput = presenterOutput;
    }
}
