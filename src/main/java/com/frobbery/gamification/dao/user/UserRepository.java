package com.frobbery.gamification.dao.user;

import com.frobbery.gamification.dao.entity.Level;
import com.frobbery.gamification.dao.entity.User;

import java.time.LocalDate;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findUserByNickNameOrEmail(String nickName, String email);

    void save(User user);

    Optional<User> findByEmail(String email);

    int getLastAvailableLevelByEmail(String email);

    void updateEntryPeriodByEmail(String email, int currentEntryPeriod);

    void updateAuthorizationDateByEmail(String email, LocalDate now);

    void addNewLevelToUser(Optional<User> user, Optional<Level> newLevel);
}
