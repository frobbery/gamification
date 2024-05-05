package com.frobbery.gamification.ui.presenter.personal_cabinet;

import com.frobbery.gamification.ui.interactor.UIInteractor;
import com.frobbery.gamification.util.dto.ReceivedAchievementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonalCabinetPresenterInputImpl implements PersonalCabinetPresenterInput{

    private final UIInteractor interactor;

    @Override
    public List<ReceivedAchievementDto> getReceivedAchievementOfUser(Authentication authentication) {
        var userEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        return interactor.getReceivedAchievementOfUser(userEmail);
    }

    @Override
    public void setPresenterOutput(PersonalCabinetPresenterOutput presenterOutput) {}
}
