package com.frobbery.gamification.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int number;

    private String description;

    private String hint;

    @Column(name = "initial_code")
    private String initialCode;

    @Column(name = "success_message")
    private String successMessage;

    @OneToOne
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;
}
