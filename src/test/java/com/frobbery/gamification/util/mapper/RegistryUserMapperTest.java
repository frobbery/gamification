package com.frobbery.gamification.util.mapper;

import com.frobbery.gamification.dao.entity.User;
import com.frobbery.gamification.util.dto.RegistryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistryUserMapperTest {

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private RegistryUserMapper sut;

    @Test
    void shouldMapRegistryDtoToUser() {
        //given
        var registryDto = RegistryDto.builder()
                .nickName("nickName")
                .email("email")
                .password("password")
                .build();
        var encodedPassword = "encodedPassword";
        when(encoder.encode(registryDto.getPassword())).thenReturn(encodedPassword);
        var expectedUser = User.builder()
                .nickName(registryDto.getNickName())
                .email(registryDto.getEmail())
                .password(encodedPassword)
                .lastAuthorizationDate(LocalDate.now())
                .currentEntryPeriod(1)
                .build();

        //when
        var actualUser = sut.map(registryDto);

        //then
        assertThat(actualUser)
                .usingRecursiveComparison()
                .isEqualTo(expectedUser);
    }

    @Test
    void shouldMapRegistryDtoToUserIfNull() {
        //when
        var result = sut.map(null);

        //then
        assertThat(result).isNull();
    }
}