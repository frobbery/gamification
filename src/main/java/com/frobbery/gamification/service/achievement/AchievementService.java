package com.frobbery.gamification.service.achievement;

import com.frobbery.gamification.util.dto.AchievementDto;
import com.frobbery.gamification.util.dto.ReceivedAchievementDto;

import java.util.List;

public interface AchievementService {

    List<AchievementDto> getNewTimedAchievementsOfUser(String userEmail);

    List<ReceivedAchievementDto> getReceivedAchievementOfUser(String userEmail);

    void addAchievementToUser(String userEmail, String achievementName);
}
