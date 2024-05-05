package com.frobbery.gamification.util.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AchievementDto {

    private String name;

    private String description;

}
