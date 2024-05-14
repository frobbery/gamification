package com.frobbery.gamification.dao;

import com.frobbery.gamification.dao.entity.LevelTest;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LevelTestRepository extends CrudRepository<LevelTest, Long> {

    Optional<LevelTest> findByLevelNumber(int levelNumber);
}
