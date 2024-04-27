package com.frobbery.gamification.service;

import com.frobbery.gamification.dto.AuthorizeDto;
import com.frobbery.gamification.dto.RegistryDto;

public interface UserService {

    boolean checkIfExists(String nickName, String email);

    void register(RegistryDto registryDto);

    boolean authorize(AuthorizeDto authorizeDto);
}
