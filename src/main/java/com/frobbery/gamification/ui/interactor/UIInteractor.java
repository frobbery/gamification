package com.frobbery.gamification.ui.interactor;

import com.frobbery.gamification.util.dto.AchievementDto;
import com.frobbery.gamification.util.dto.AuthorizeDto;
import com.frobbery.gamification.util.dto.CheckCodeDto;
import com.frobbery.gamification.util.dto.LevelDto;
import com.frobbery.gamification.util.dto.ReceivedAchievementDto;
import com.frobbery.gamification.util.dto.RegistryDto;

import java.util.List;

public interface UIInteractor {

    boolean checkIfUserExists(RegistryDto registryDto);

    void registerUser(RegistryDto registryDto);

    void authorizeUser(AuthorizeDto authorizeDto);

    long getAvailableLevelsNum();

    int getLastOpenLevelNum(String userEmail);

    LevelDto getLevelByNumber(int levelNum);

    List<AchievementDto> getNewTimeAchievementsOfUser(String userEmail);

    List<ReceivedAchievementDto> getReceivedAchievementOfUser(String userEmail);

    boolean isLastLevel(int levelNumber);

    AchievementDto addLevelAchievementToUser(String userEmail, int levelNumber, boolean levelLast);

    boolean checkCode(CheckCodeDto checkCodeDto);
}
