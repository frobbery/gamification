package com.frobbery.gamification.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class LevelTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int levelNumber;

    private String className;

    private String code;
}
