package com.frobbery.gamification.dao.user;

import com.frobbery.gamification.dao.entity.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findUserByNickNameOrEmail(String nickName, String email);

    void save(User user);

    Optional<User> findByEmail(String email);

    int getLastAvailableLevelByEmail(String email);
}
