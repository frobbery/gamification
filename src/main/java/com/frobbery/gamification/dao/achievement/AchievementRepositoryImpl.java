package com.frobbery.gamification.dao.achievement;

import com.frobbery.gamification.dao.entity.Achievement;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AchievementRepositoryImpl implements AchievementRepository {

    private List<Achievement> achievements;

    @PostConstruct
    private void initLevels() {
        achievements = new ArrayList<>();
        achievements.add(Achievement.builder()
                .id(1L)
                .name("Первая награда")
                .description("Описание первой награды")
                .entryPeriod(1)
                .build());
    }

    @Override
    public List<Achievement> getAllWithEntryPeriod(int entryPeriod) {
        return achievements.stream()
                .filter(achievement -> achievement.getEntryPeriod() == entryPeriod)
                .collect(Collectors.toList());
    }
}
