package com.frobbery.gamification.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private long id;

    private String nickName;

    private String email;

    private String passWord;
}
