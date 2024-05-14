package com.frobbery.gamification.util.mapper;

import com.frobbery.gamification.dao.entity.Level;
import com.frobbery.gamification.util.dto.LevelDto;
import org.springframework.stereotype.Component;

@Component
public class LevelDtoMapper implements Mapper<Level, LevelDto> {
    @Override
    public LevelDto map(Level level) {
        return LevelDto.builder()
                .number(level.getNumber())
                .description(level.getDescription())
                .hint(level.getHint())
                .initialCode(level.getInitialCode().replace("\\n", "\n").replace("\\t", "\t"))
                .successMessage(level.getSuccessMessage())
                .build();
    }
}
