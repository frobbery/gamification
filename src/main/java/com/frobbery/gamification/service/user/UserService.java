package com.frobbery.gamification.service.user;

import com.frobbery.gamification.util.dto.AuthorizeDto;
import com.frobbery.gamification.util.dto.RegistryDto;

public interface UserService {

    boolean checkIfExists(String nickName, String email);

    void register(RegistryDto registryDto);

    void authorize(AuthorizeDto authorizeDto);


}
