package com.frobbery.gamification.dao.level;

import org.springframework.stereotype.Repository;

@Repository
public class LevelRepositoryMock implements LevelRepository {
    @Override
    public int count() {
        return 4;
    }
}
