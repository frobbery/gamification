package com.frobbery.gamification.dao.received_achievement;

import com.frobbery.gamification.dao.entity.ReceivedAchievement;

import java.util.List;

public interface ReceivedAchievementRepository {

    void save(ReceivedAchievement receivedAchievement);

    List<ReceivedAchievement> getAllAchievementsOfUserId(long userId);
}
