package com.frobbery.gamification.ui.presenter.personal_cabinet;

import com.frobbery.gamification.ui.presenter.PresenterInput;
import com.frobbery.gamification.util.dto.ReceivedAchievementDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PersonalCabinetPresenterInput extends PresenterInput<PersonalCabinetPresenterOutput> {
    List<ReceivedAchievementDto> getReceivedAchievementOfUser(Authentication authentication);
}
