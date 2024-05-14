package com.frobbery.gamification.dao;

import com.frobbery.gamification.dao.entity.Achievement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AchievementRepository extends CrudRepository<Achievement, Long> {

    List<Achievement> findAllByEntryPeriod(int entryPeriod);

    Optional<Achievement> findByName(String name);
}
