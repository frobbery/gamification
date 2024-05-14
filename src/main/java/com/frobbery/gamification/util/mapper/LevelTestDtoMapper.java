package com.frobbery.gamification.util.mapper;

import com.frobbery.gamification.dao.entity.LevelTest;
import com.frobbery.gamification.util.dto.LevelTestDto;
import org.springframework.stereotype.Component;

@Component
public class LevelTestDtoMapper implements Mapper<LevelTest, LevelTestDto> {
    @Override
    public LevelTestDto map(LevelTest levelTest) {
        return LevelTestDto.builder()
                .className(levelTest.getClassName())
                .code(levelTest.getCode().replace("\\n", "\n"))
                .build();
    }
}
