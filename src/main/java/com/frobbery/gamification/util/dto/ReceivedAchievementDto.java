package com.frobbery.gamification.util.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReceivedAchievementDto {

    private String name;

    private String description;

    private LocalDate receivedDate;
}
