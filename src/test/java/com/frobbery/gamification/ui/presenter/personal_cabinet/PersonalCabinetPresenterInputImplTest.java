package com.frobbery.gamification.ui.presenter.personal_cabinet;

import com.frobbery.gamification.dao.entity.User;
import com.frobbery.gamification.service.user.authentication.UserDetailsImpl;
import com.frobbery.gamification.ui.interactor.UIInteractor;
import com.frobbery.gamification.util.dto.ReceivedAchievementDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonalCabinetPresenterInputImplTest {

    @Mock
    private UIInteractor interactor;

    @InjectMocks
    private PersonalCabinetPresenterInputImpl sut;

    @Test
    void shouldSetPresenterOutput() {
        sut.setPresenterOutput(mock(PersonalCabinetPresenterOutput.class));
    }

    @Test
    void shouldGetReceivedAchievementOfUser() {
        //given
        var authentication = mock(Authentication.class);
        var userEmail = "userEmail";
        var expectedReceivedAchievements = List.of(mock(ReceivedAchievementDto.class));
        when(authentication.getPrincipal()).thenReturn(new UserDetailsImpl(User.builder()
                .email(userEmail)
                .build()));
        when(interactor.getReceivedAchievementOfUser(userEmail)).thenReturn(expectedReceivedAchievements);

        //when
        var actualReceivedAchievements = sut.getReceivedAchievementOfUser(authentication);

        //then
        assertThat(actualReceivedAchievements)
                .usingRecursiveComparison()
                .isEqualTo(expectedReceivedAchievements);
    }
}