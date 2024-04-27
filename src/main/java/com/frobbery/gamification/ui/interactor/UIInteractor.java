package com.frobbery.gamification.ui.interactor;

import com.frobbery.gamification.dto.AuthorizeDto;
import com.frobbery.gamification.dto.RegistryDto;

public interface UIInteractor {

    boolean checkIfUserExists(RegistryDto registryDto);

    void registerUser(RegistryDto registryDto);

    boolean authorizeUser(AuthorizeDto authorizeDto);
}
