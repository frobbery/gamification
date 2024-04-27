package com.frobbery.gamification.service;

import com.frobbery.gamification.dao.user.UserRepository;
import com.frobbery.gamification.dto.AuthorizeDto;
import com.frobbery.gamification.dto.RegistryDto;
import com.frobbery.gamification.entity.User;
import com.frobbery.gamification.exception.UserNotFoundException;
import com.frobbery.gamification.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

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
    public boolean authorize(AuthorizeDto authorizeDto) {
        var user = userRepository.findByEmail(authorizeDto.getEmail()).orElseThrow(
                () -> new UserNotFoundException("Пользователь с данным E-Mail не найден"));
        return encoder.encode(authorizeDto.getPassword()).equals(user.getPassWord());
    }
}
