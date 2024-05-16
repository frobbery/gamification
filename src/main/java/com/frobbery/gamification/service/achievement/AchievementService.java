package com.frobbery.gamification.service.achievement;

import com.frobbery.gamification.util.dto.AchievementDto;
import com.frobbery.gamification.util.dto.ReceivedAchievementDto;

import java.util.List;

public interface AchievementService {

    List<AchievementDto> getNewTimedAchievementsOfUser(String userEmail);

    List<ReceivedAchievementDto> getReceivedAchievementOfUser(String userEmail);

    boolean addAchievementToUser(String userEmail, String achievementName);
}
