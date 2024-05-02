package com.frobbery.gamification.ui.interactor;

import com.frobbery.gamification.util.dto.AuthorizeDto;
import com.frobbery.gamification.util.dto.RegistryDto;

public interface UIInteractor {

    boolean checkIfUserExists(RegistryDto registryDto);

    void registerUser(RegistryDto registryDto);

    void authorizeUser(AuthorizeDto authorizeDto);

    int getAvailableLevelsNum();

    int getLastOpenLevelNum(String userEmail);
}
