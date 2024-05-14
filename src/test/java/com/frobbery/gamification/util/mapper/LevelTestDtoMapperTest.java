package com.frobbery.gamification.util.mapper;

import com.frobbery.gamification.dao.entity.LevelTest;
import com.frobbery.gamification.util.dto.LevelTestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LevelTestDtoMapperTest {

    private final LevelTestDtoMapper sut = new LevelTestDtoMapper();

    @Test
    void shouldMapLevelTestToLevelTestDto() {
        //given
        var levelTest = LevelTest.builder()
                .levelNumber(0)
                .className("className")
                .code("code\\n")
                .build();
        var expectedLevelTestDto = LevelTestDto.builder()
                .className(levelTest.getClassName())
                .code("code\n")
                .build();

        //when
        var actualLevelTestDto = sut.map(levelTest);

        //then
        assertThat(actualLevelTestDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedLevelTestDto);
    }
}