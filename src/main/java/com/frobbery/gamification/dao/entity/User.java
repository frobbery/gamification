package com.frobbery.gamification.dao.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {

    private long id;

    private String nickName;

    private String email;

    private String passWord;

    private List<Level> levels;
}
