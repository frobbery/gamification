package com.frobbery.gamification.dao.level;

import com.frobbery.gamification.dao.entity.Level;

import java.util.Optional;

public interface LevelRepository {

    int count();

    Optional<Level> findByNumber(int number);
}
