package com.frobbery.gamification.dao;

import com.frobbery.gamification.dao.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select u from User u where u.email = :email or u.nickName = :nickName")
    Optional<User> findByNickNameOrEmail(String nickName, String email);

    @Query("select u from User u where u.email = :email")
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("update User u set u.currentEntryPeriod = :currentEntryPeriod where u.email = :email")
    void updateEntryPeriodByEmail(String email, int currentEntryPeriod);

    @Modifying
    @Query("update User u set u.lastAuthorizationDate = :now where u.email = :email")
    void updateAuthorizationDateByEmail(String email, LocalDate now);

    @Query("select size(u.levels) from User u where u.email = :email")
    int getLastOpenLevelNumberByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.levels WHERE u.email = (:email)")
    Optional<User> findByEmailAndFetchLevelsEagerly(@Param("email") String email);
}
