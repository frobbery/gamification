package com.frobbery.gamification.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nick_name")
    private String nickName;

    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "last_authorization_date")
    private LocalDate lastAuthorizationDate;

    @Column(name = "current_entry_period")
    private int currentEntryPeriod;

    @ManyToMany(targetEntity = Level.class, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 5)
    @JoinTable(name = "user_level", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "level_id"))
    private List<Level> levels;
}
