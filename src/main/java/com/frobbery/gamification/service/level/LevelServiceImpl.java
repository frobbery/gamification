package com.frobbery.gamification.service.level;

import com.frobbery.gamification.dao.entity.Achievement;
import com.frobbery.gamification.dao.entity.Level;
import com.frobbery.gamification.dao.level.LevelRepository;
import com.frobbery.gamification.dao.user.UserRepository;
import com.frobbery.gamification.util.dto.AchievementDto;
import com.frobbery.gamification.util.dto.LevelDto;
import com.frobbery.gamification.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;

    private final UserRepository userRepository;

    private final Mapper<Level, LevelDto> mapper;

    private final Mapper<Achievement, AchievementDto> achievementMapper;

    @Override
    public int getAllAvailableNum() {
        return levelRepository.count();
    }

    @Override
    public int getLastOpenNum(String userEmail) {
        return userRepository.getLastAvailableLevelByEmail(userEmail);
    }

    @Override
    public LevelDto getByNumber(int levelNum) {
        return levelRepository.findByNumber(levelNum).map(mapper::map).orElseThrow();
    }

    @Override
    public boolean isLast(int levelNumber) {
        return levelRepository.count() == levelNumber;
    }

    @Override
    public AchievementDto getLevelAchievement(int levelNumber) {
        return levelRepository.findByNumber(levelNumber)
                .map(level -> achievementMapper.map(level.getAchievement()))
                .orElseThrow();
    }

    @Override
    public void addNewLevelToUser(String userEmail, int levelNumber) {
        var newLevel = levelRepository.findByNumber(levelNumber);
        var user = userRepository.findByEmail(userEmail);
        userRepository.addNewLevelToUser(user, newLevel);
    }
}
