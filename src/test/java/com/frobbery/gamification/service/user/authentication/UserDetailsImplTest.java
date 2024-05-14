package com.frobbery.gamification.service.user.authentication;

import com.frobbery.gamification.dao.entity.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDetailsImplTest {

    @Test
    void shouldCreateUserDetailsImplFromUser() {
        //given
        var user = User.builder()
                .email("email")
                .password("password")
                .build();

        //when
        var userDetailsImpl = new UserDetailsImpl(user);

        //then
        assertEquals(user.getEmail(), userDetailsImpl.getUsername());
        assertEquals(user.getPassword(), userDetailsImpl.getPassword());
        assertEquals(List.of(), userDetailsImpl.getAuthorities());
        assertTrue(userDetailsImpl.isAccountNonExpired());
        assertTrue(userDetailsImpl.isAccountNonLocked());
        assertTrue(userDetailsImpl.isCredentialsNonExpired());
        assertTrue(userDetailsImpl.isEnabled());
    }
}