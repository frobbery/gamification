package com.frobbery.gamification.mapper;

import com.frobbery.gamification.dto.RegistryDto;
import com.frobbery.gamification.entity.User;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class UserRegistryMapper implements Mapper<RegistryDto, User> {
    @Override
    public User map(RegistryDto registryDto) {
        if (isNull(registryDto)) {
            return null;
        }
        return User.builder().nickName(registryDto.getNickName())
                .email(registryDto.getEmail())
                .passWord(registryDto.getPassword())
                .build();
    }
}
