package com.frobbery.gamification.service.user;

import com.frobbery.gamification.dao.UserRepository;
import com.frobbery.gamification.dao.entity.User;
import com.frobbery.gamification.util.dto.AuthorizeDto;
import com.frobbery.gamification.util.dto.RegistryDto;
import com.frobbery.gamification.util.mapper.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private AuthenticationProvider authenticationProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper<RegistryDto, User> registryMapper;

    @InjectMocks
    private UserServiceImpl sut;

    @Test
    void shouldCheckIfExists() {
        //given
        var nickName = "nickName";
        var email = "email";
        when(userRepository.findByNickNameOrEmail(nickName, email)).thenReturn(Optional.of(mock(User.class)));

        //when
        var result = sut.checkIfExists(nickName, email);

        //then
        assertTrue(result);
    }

    @Test
    void shouldRegister() {
        //given
        var registryDto = mock(RegistryDto.class);
        var user = mock(User.class);
        when(registryMapper.map(registryDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        //when
        sut.register(registryDto);
    }

    @Test
    void shouldAuthorize() {
        //given
        var authorizeDto = AuthorizeDto.builder()
                .email("email")
                .password("password")
                .build();
        var authentication = mock(Authentication.class);
        when(authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(authorizeDto.getEmail(), authorizeDto.getPassword())))
                .thenReturn(authentication);

        //when
        sut.authorize(authorizeDto);

        //then
        assertThat(SecurityContextHolder.getContext().getAuthentication())
                .usingRecursiveComparison()
                .isEqualTo(authentication);
    }

    @Test
    void shouldUpdateTimePeriod_whenEnteredYesterday() {
        //given
        var email = "email";
        var user = User.builder()
                .lastAuthorizationDate(LocalDate.now().minusDays(1))
                .currentEntryPeriod(99)
                .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        //when
        sut.updateTimePeriod(email);

        //then
        verify(userRepository, times(1)).updateEntryPeriodByEmail(email, 100);
        verify(userRepository, times(1)).updateAuthorizationDateByEmail(email, LocalDate.now());
    }

    @ParameterizedTest
    @MethodSource("notYesterdayDate")
    void shouldUpdateTimePeriod_whenNotEnteredYesterday(LocalDate lastAuthorizationDate) {
        //given
        var email = "email";
        var user = User.builder()
                .lastAuthorizationDate(lastAuthorizationDate)
                .currentEntryPeriod(99)
                .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        //when
        sut.updateTimePeriod(email);

        //then
        verify(userRepository, times(1)).updateEntryPeriodByEmail(email, 1);
        verify(userRepository, times(1)).updateAuthorizationDateByEmail(email, LocalDate.now());
    }

    private static Stream<Arguments> notYesterdayDate() {
        return Stream.of(
                Arguments.of(LocalDate.now().minusYears(1)),
                Arguments.of(LocalDate.now().minusMonths(1)),
                Arguments.of(LocalDate.now().minusDays(2))
        );
    }

    @Test
    void shouldNotUpdateTimePeriod_whenEnteredToday() {
        //given
        var email = "email";
        var user = User.builder()
                .lastAuthorizationDate(LocalDate.now())
                .currentEntryPeriod(99)
                .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        //when
        sut.updateTimePeriod(email);

        //then
        verify(userRepository, times(0)).updateEntryPeriodByEmail(email, 1);
        verify(userRepository, times(1)).updateAuthorizationDateByEmail(email, LocalDate.now());
    }
}