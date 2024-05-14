package com.frobbery.gamification.util.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckCodeDto {

    private int levelNumber;

    private String initialCode;

    private String code;
}
