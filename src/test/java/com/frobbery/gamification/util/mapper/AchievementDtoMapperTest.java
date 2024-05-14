package com.frobbery.gamification.util.mapper;

import com.frobbery.gamification.dao.entity.Achievement;
import com.frobbery.gamification.util.dto.AchievementDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AchievementDtoMapperTest {

    private final AchievementDtoMapper sut = new AchievementDtoMapper();

    @Test
    void shouldMapAchievementToAchievementDto() {
        //given
        var achievement = Achievement.builder()
                .name("name")
                .description("description")
                .build();
        var expectedAchievementDto = AchievementDto.builder()
                .name(achievement.getName())
                .description(achievement.getDescription())
                .build();

        //when
        var actualAchievementDto = sut.map(achievement);

        //then
        assertThat(actualAchievementDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedAchievementDto);
    }

}