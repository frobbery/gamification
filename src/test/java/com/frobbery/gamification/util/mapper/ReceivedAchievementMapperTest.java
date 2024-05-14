package com.frobbery.gamification.util.mapper;

import com.frobbery.gamification.dao.entity.Achievement;
import com.frobbery.gamification.dao.entity.ReceivedAchievement;
import com.frobbery.gamification.util.dto.ReceivedAchievementDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ReceivedAchievementMapperTest {

    private final ReceivedAchievementMapper sut = new ReceivedAchievementMapper();

    @Test
    void shouldMapReceivedAchievementToReceivedAchievementDto() {
        //given
        var receivedAchievement = ReceivedAchievement.builder()
                .achievement(Achievement.builder()
                        .name("name")
                        .description("description")
                        .build())
                .receiveDate(LocalDate.now())
                .build();
        var expectedReceivedAchievementDto = ReceivedAchievementDto.builder()
                .name(receivedAchievement.getAchievement().getName())
                .description(receivedAchievement.getAchievement().getDescription())
                .receivedDate(receivedAchievement.getReceiveDate());

        //when
        var actualReceivedAchievementDto = sut.map(receivedAchievement);

        //then
        assertThat(actualReceivedAchievementDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedReceivedAchievementDto);
    }

}