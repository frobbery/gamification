package com.frobbery.gamification.dao.user;

import com.frobbery.gamification.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryMock implements UserRepository {

    private final PasswordEncoder encoder;

    private List<User> users;

    @PostConstruct
    private void initUsers() {
        users = new ArrayList<>();
        users.add(User.builder()
                .nickName("nick")
                .email("email@gmail.com")
                .passWord(encoder.encode("password"))
                .build());
    }
    @Override
    public Optional<User> findUserByNickNameOrEmail(String nickName, String email) {
        return users.stream()
                .filter(user -> user.getNickName().equals(nickName) || user.getEmail().equals(email))
                .findAny();
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }
}
