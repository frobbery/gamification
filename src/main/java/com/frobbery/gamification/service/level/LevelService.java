package com.frobbery.gamification.service.level;

import com.frobbery.gamification.util.dto.AchievementDto;
import com.frobbery.gamification.util.dto.LevelDto;

public interface LevelService {

    int getAllAvailableNum();

    int getLastOpenNum(String userEmail);

    LevelDto getByNumber(int levelNum);

    boolean isLast(int levelNumber);

    AchievementDto getLevelAchievement(int levelNumber);

    void addNewLevelToUser(String userEmail, int levelNumber);
}
