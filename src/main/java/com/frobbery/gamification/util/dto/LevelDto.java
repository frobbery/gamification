package com.frobbery.gamification.util.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LevelDto {

    private int number;

    private String description;

    private String hint;

    private String initialCode;

    private String successMessage;
}
