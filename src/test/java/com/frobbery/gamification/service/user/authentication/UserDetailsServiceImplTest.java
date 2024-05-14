package com.frobbery.gamification.service.user.authentication;

import com.frobbery.gamification.dao.UserRepository;
import com.frobbery.gamification.dao.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl sut;

    @Test
    void shouldReturnUserDetails_whenUserFound() {
        //given
        var email = "email";
        var user = User.builder().build();
        var expectedUserDetails = new UserDetailsImpl(user);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        //when
        var actualUserDetailsImpl = sut.loadUserByUsername(email);

        //then
        assertThat(actualUserDetailsImpl)
                .usingRecursiveComparison()
                .isEqualTo(expectedUserDetails);
    }

    @Test
    void shouldReturnNull_whenUserNotFound() {
        //given
        var email = "email";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        //when
        var actualUserDetailsImpl = sut.loadUserByUsername(email);

        //then
        assertThat(actualUserDetailsImpl)
                .isNull();
    }
}