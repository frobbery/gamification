package com.frobbery.gamification.util.mapper;

import com.frobbery.gamification.dao.entity.Achievement;
import com.frobbery.gamification.util.dto.AchievementDto;
import org.springframework.stereotype.Component;

@Component
public class AchievementDtoMapper implements Mapper<Achievement, AchievementDto>{
    @Override
    public AchievementDto map(Achievement achievement) {
        return AchievementDto.builder()
                .name(achievement.getName())
                .description(achievement.getDescription())
                .build();
    }
}
