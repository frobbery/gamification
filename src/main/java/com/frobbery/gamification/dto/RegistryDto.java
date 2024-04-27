package com.frobbery.gamification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistryDto {

    private String email;

    private String nickName;

    private String password;
}
