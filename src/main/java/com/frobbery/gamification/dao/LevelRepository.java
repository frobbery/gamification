package com.frobbery.gamification.dao;

import com.frobbery.gamification.dao.entity.Level;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LevelRepository extends CrudRepository<Level, Long> {

    Optional<Level> findByNumber(int number);
}
