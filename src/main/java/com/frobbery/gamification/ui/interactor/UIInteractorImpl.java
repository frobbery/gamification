package com.frobbery.gamification.ui.interactor;

import com.frobbery.gamification.service.achievement.AchievementService;
import com.frobbery.gamification.service.level.LevelService;
import com.frobbery.gamification.util.dto.AchievementDto;
import com.frobbery.gamification.util.dto.AuthorizeDto;
import com.frobbery.gamification.util.dto.LevelDto;
import com.frobbery.gamification.util.dto.ReceivedAchievementDto;
import com.frobbery.gamification.util.dto.RegistryDto;
import com.frobbery.gamification.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UIInteractorImpl implements UIInteractor{

    private final UserService userService;

    private final LevelService levelService;

    private final AchievementService achievementService;

    @Override
    public boolean checkIfUserExists(RegistryDto registryDto) {
        return userService.checkIfExists(registryDto.getNickName(), registryDto.getEmail());
    }

    @Override
    public void registerUser(RegistryDto registryDto) {
        userService.register(registryDto);
    }

    @Override
    public void authorizeUser(AuthorizeDto authorizeDto) {
        userService.authorize(authorizeDto);
    }

    @Override
    public int getAvailableLevelsNum() {
        return levelService.getAllAvailableNum();
    }

    @Override
    public int getLastOpenLevelNum(String userEmail) {
        return levelService.getLastOpenNum(userEmail);
    }

    @Override
    public LevelDto getLevelByNumber(int levelNum) {
        return levelService.getByNumber(levelNum);
    }

    @Override
    public List<AchievementDto> getNewTimeAchievementsOfUser(String userEmail) {
        return achievementService.getNewTimedAchievementsOfUser(userEmail);
    }

    @Override
    public List<ReceivedAchievementDto> getReceivedAchievementOfUser(String userEmail) {
        return achievementService.getReceivedAchievementOfUser(userEmail);
    }

    @Override
    public boolean isLastLevel(int levelNumber) {
        return levelService.isLast(levelNumber);
    }

    @Override
    public AchievementDto addLevelAchievementToUser(String userEmail, int levelNumber) {
        var achievement = levelService.getLevelAchievement(levelNumber);
        levelService.addNewLevelToUser(userEmail, levelNumber + 1);
        return achievement;
    }
}
