package com.frobbery.gamification.ui.interactor;

import com.frobbery.gamification.service.level.LevelService;
import com.frobbery.gamification.util.dto.AuthorizeDto;
import com.frobbery.gamification.util.dto.RegistryDto;
import com.frobbery.gamification.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UIInteractorImpl implements UIInteractor{

    private final UserService userService;

    private final LevelService levelService;

    @Override
    public boolean checkIfUserExists(RegistryDto registryDto) {
        return userService.checkIfExists(registryDto.getNickName(), registryDto.getEmail());
    }

    @Override
    public void registerUser(RegistryDto registryDto) {
        userService.register(registryDto);
    }

    @Override
    public void authorizeUser(AuthorizeDto authorizeDto) {
        userService.authorize(authorizeDto);
    }

    @Override
    public int getAvailableLevelsNum() {
        return levelService.getAllAvailableNum();
    }

    @Override
    public int getLastOpenLevelNum(String userEmail) {
        return levelService.getLastOpenNum(userEmail);
    }
}
