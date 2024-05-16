package com.frobbery.gamification.service.achievement;

import com.frobbery.gamification.dao.AchievementRepository;
import com.frobbery.gamification.dao.entity.Achievement;
import com.frobbery.gamification.dao.entity.ReceivedAchievement;
import com.frobbery.gamification.dao.ReceivedAchievementRepository;
import com.frobbery.gamification.dao.UserRepository;
import com.frobbery.gamification.util.dto.AchievementDto;
import com.frobbery.gamification.util.dto.ReceivedAchievementDto;
import com.frobbery.gamification.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final UserRepository userRepository;

    private final AchievementRepository achievementRepository;

    private final ReceivedAchievementRepository receivedAchievementRepository;

    private final Mapper<Achievement, AchievementDto> achievementMapper;

    private final Mapper<ReceivedAchievement, ReceivedAchievementDto> receivedAchievementMapper;

    @Override
    public List<AchievementDto> getNewTimedAchievementsOfUser(String userEmail) {
        var user = userRepository.findByEmail(userEmail).orElseThrow();
        var receivedAchievementsIds = receivedAchievementRepository.findAllByUserId(user.getId()).stream()
                .map(receivedAchievement -> receivedAchievement.getAchievement().getId()).toList();
        var achievements = achievementRepository.findAllByEntryPeriod(user.getCurrentEntryPeriod()).stream()
                .filter(achievement -> !receivedAchievementsIds.contains(achievement.getId())).toList();

        achievements
                .forEach(achievement -> receivedAchievementRepository.save(
                        ReceivedAchievement.builder()
                                .user(user)
                                .achievement(achievement)
                                .receiveDate(LocalDate.now())
                                .build()));
        return achievements.stream().map(achievementMapper::map).collect(Collectors.toList());
    }

    @Override
    public List<ReceivedAchievementDto> getReceivedAchievementOfUser(String userEmail) {
        var userId = userRepository.findByEmail(userEmail).orElseThrow().getId();
        return receivedAchievementRepository.findAllByUserId(userId).stream()
                .map(receivedAchievementMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public boolean addAchievementToUser(String userEmail, String achievementName) {
        var user = userRepository.findByEmail(userEmail).orElseThrow();
        var receivedAchievementsIds = receivedAchievementRepository.findAllByUserId(user.getId()).stream()
                .map(receivedAchievement -> receivedAchievement.getAchievement().getId()).toList();
        var achievement = achievementRepository.findByName(achievementName).orElseThrow();
        if (!receivedAchievementsIds.contains(achievement.getId())) {
            receivedAchievementRepository.save(
                    ReceivedAchievement.builder()
                            .user(userRepository.findByEmail(userEmail).orElseThrow())
                            .achievement(achievementRepository.findByName(achievementName).orElseThrow())
                            .receiveDate(LocalDate.now())
                            .build());
            return true;
        }
        return false;
    }
}
