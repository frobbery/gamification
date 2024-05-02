package com.frobbery.gamification.util.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistryDto {

    private String email;

    private String nickName;

    private String password;
}
