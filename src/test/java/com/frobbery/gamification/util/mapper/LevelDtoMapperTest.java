package com.frobbery.gamification.util.mapper;

import com.frobbery.gamification.dao.entity.Level;
import com.frobbery.gamification.util.dto.LevelDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LevelDtoMapperTest {

    private final LevelDtoMapper sut = new LevelDtoMapper();

    @Test
    void shouldMapLevelToLevelDto() {
        //given
        var level = Level.builder()
                .number(0)
                .description("description")
                .hint("hint")
                .initialCode("initialCode\\n")
                .successMessage("successMessage")
                .build();
        var expectedLevelDto = LevelDto.builder()
                .number(level.getNumber())
                .description(level.getDescription())
                .hint(level.getHint())
                .initialCode("initialCode\n")
                .successMessage(level.getSuccessMessage())
                .build();

        //when
        var actualLevelDto = sut.map(level);

        //then
        assertThat(actualLevelDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedLevelDto);
    }
}