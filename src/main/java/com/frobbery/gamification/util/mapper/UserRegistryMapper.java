package com.frobbery.gamification.util.mapper;

import com.frobbery.gamification.util.dto.RegistryDto;
import com.frobbery.gamification.dao.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class UserRegistryMapper implements Mapper<RegistryDto, User> {

    private final PasswordEncoder encoder;

    @Override
    public User map(RegistryDto registryDto) {
        if (isNull(registryDto)) {
            return null;
        }
        return User.builder().nickName(registryDto.getNickName())
                .email(registryDto.getEmail())
                .passWord(encoder.encode(registryDto.getPassword()))
                .lastAuthorizationDate(LocalDate.now())
                .currentEntryPeriod(1)
                .build();
    }
}
