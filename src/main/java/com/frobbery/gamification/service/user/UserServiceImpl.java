package com.frobbery.gamification.service.user;

import com.frobbery.gamification.dao.entity.User;
import com.frobbery.gamification.dao.user.UserRepository;
import com.frobbery.gamification.util.dto.AuthorizeDto;
import com.frobbery.gamification.util.dto.RegistryDto;
import com.frobbery.gamification.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationProvider authenticationProvider;

    private final UserRepository userRepository;

    private final Mapper<RegistryDto, User> registryMapper;

    @Override
    public boolean checkIfExists(String nickName, String email) {
        return userRepository.findUserByNickNameOrEmail(nickName, email).isPresent();
    }

    @Override
    public void register(RegistryDto registryDto) {
        userRepository.save(registryMapper.map(registryDto));
    }

    @Override
    public void authorize(AuthorizeDto authorizeDto) {
        var authToken = new UsernamePasswordAuthenticationToken(authorizeDto.getEmail(), authorizeDto.getPassword());
        var authentication = authenticationProvider.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
