package com.frobbery.gamification.ui.interactor;

import com.frobbery.gamification.dto.AuthorizeDto;
import com.frobbery.gamification.dto.RegistryDto;
import com.frobbery.gamification.exception.UserNotFoundException;
import com.frobbery.gamification.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UIInteractorImpl implements UIInteractor{

    private final UserService userService;

    @Override
    public boolean checkIfUserExists(RegistryDto registryDto) {
        return userService.checkIfExists(registryDto.getNickName(), registryDto.getEmail());
    }

    @Override
    public void registerUser(RegistryDto registryDto) {
        userService.register(registryDto);
    }

    @Override
    public boolean authorizeUser(AuthorizeDto authorizeDto) {
        return userService.authorize(authorizeDto);
    }
}
