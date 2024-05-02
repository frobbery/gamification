package com.frobbery.gamification.service.user.authentication;

import com.frobbery.gamification.dao.user.UserRepository;
import com.frobbery.gamification.util.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            var foundUser = userRepository.findByEmail(email);
            if (foundUser.isPresent()) {
                return new UserDetailsImpl(foundUser.get());
            } else {
                throw new UserNotFoundException("Пользователь с данным email не найден");
            }
        } catch (Exception e) {
            return null;
        }
    }
}
