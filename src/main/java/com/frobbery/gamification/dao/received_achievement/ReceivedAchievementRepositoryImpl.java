package com.frobbery.gamification.dao.received_achievement;

import com.frobbery.gamification.dao.entity.ReceivedAchievement;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReceivedAchievementRepositoryImpl implements ReceivedAchievementRepository {

    List<ReceivedAchievement> receivedAchievementList = new ArrayList<>();

    @Override
    public void save(ReceivedAchievement receivedAchievement) {
        receivedAchievementList.add(receivedAchievement);
    }

    @Override
    public List<ReceivedAchievement> getAllAchievementsOfUserId(long userId) {
        return receivedAchievementList.stream()
                .filter(receivedAchievement -> receivedAchievement.getUser().getId() == userId)
                .collect(Collectors.toList());
    }
}
