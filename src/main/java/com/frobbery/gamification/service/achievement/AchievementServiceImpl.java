package com.frobbery.gamification.service.achievement;

import com.frobbery.gamification.dao.achievement.AchievementRepository;
import com.frobbery.gamification.dao.entity.Achievement;
import com.frobbery.gamification.dao.entity.ReceivedAchievement;
import com.frobbery.gamification.dao.received_achievement.ReceivedAchievementRepository;
import com.frobbery.gamification.dao.user.UserRepository;
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
        var achievements = achievementRepository.getAllWithEntryPeriod(user.getCurrentEntryPeriod());
        var receivedAchievements = receivedAchievementRepository.getAllAchievementsOfUserId(user.getId());
        receivedAchievements
                .forEach(receivedAchievement -> achievements.remove(receivedAchievement.getAchievement()));

        achievements.forEach(achievement -> receivedAchievementRepository.save(
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
        return receivedAchievementRepository.getAllAchievementsOfUserId(userId).stream()
                .map(receivedAchievementMapper::map)
                .collect(Collectors.toList());
    }
}
