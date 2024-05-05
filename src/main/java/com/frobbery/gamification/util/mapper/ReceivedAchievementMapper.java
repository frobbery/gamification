package com.frobbery.gamification.util.mapper;

import com.frobbery.gamification.dao.entity.ReceivedAchievement;
import com.frobbery.gamification.util.dto.ReceivedAchievementDto;
import org.springframework.stereotype.Component;

@Component
public class ReceivedAchievementMapper implements Mapper<ReceivedAchievement, ReceivedAchievementDto>{
    @Override
    public ReceivedAchievementDto map(ReceivedAchievement receivedAchievement) {
        return ReceivedAchievementDto.builder()
                .name(receivedAchievement.getAchievement().getName())
                .description(receivedAchievement.getAchievement().getDescription())
                .receivedDate(receivedAchievement.getReceiveDate())
                .build();
    }
}
