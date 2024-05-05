package com.frobbery.gamification.dao.achievement;

import com.frobbery.gamification.dao.entity.Achievement;

import java.util.List;

public interface AchievementRepository {

    List<Achievement> getAllWithEntryPeriod(int entryPeriod);
}
