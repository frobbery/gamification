package com.frobbery.gamification.service.user;

import com.frobbery.gamification.dao.entity.User;
import com.frobbery.gamification.dao.UserRepository;
import com.frobbery.gamification.util.dto.AuthorizeDto;
import com.frobbery.gamification.util.dto.RegistryDto;
import com.frobbery.gamification.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationProvider authenticationProvider;

    private final UserRepository userRepository;

    private final Mapper<RegistryDto, User> registryMapper;

    @Override
    public boolean checkIfExists(String nickName, String email) {
        return userRepository.findByNickNameOrEmail(nickName, email).isPresent();
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

    @Override
    @Transactional
    public void updateTimePeriod(String email) {
        var user = userRepository.findByEmail(email).orElseThrow();
        var periodBetweenLastAuthorization = Period.between(user.getLastAuthorizationDate(), LocalDate.now());
        var enteredYesterday = periodBetweenLastAuthorization.getYears() == 0 &&
                periodBetweenLastAuthorization.getMonths() == 0 && periodBetweenLastAuthorization.getDays() == 1;
        if (enteredYesterday) {
            userRepository.updateEntryPeriodByEmail(email, user.getCurrentEntryPeriod() + 1);
        } else if (!user.getLastAuthorizationDate().equals(LocalDate.now())) {
            userRepository.updateEntryPeriodByEmail(email, 1);
        }
        userRepository.updateAuthorizationDateByEmail(email, LocalDate.now());
    }
}
