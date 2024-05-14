package com.frobbery.gamification.dao;

import com.frobbery.gamification.dao.entity.ReceivedAchievement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReceivedAchievementRepository extends CrudRepository<ReceivedAchievement, Long> {

    List<ReceivedAchievement> findAllByUserId(long userId);
}
