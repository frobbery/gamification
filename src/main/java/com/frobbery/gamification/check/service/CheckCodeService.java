package com.frobbery.gamification.check.service;

import com.frobbery.gamification.util.dto.CheckCodeDto;

public interface CheckCodeService {

    boolean check(CheckCodeDto checkCodeDto);
}
