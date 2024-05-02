package com.frobbery.gamification.dao.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Level {

    private long id;
    private int num;
    private String description;
}
