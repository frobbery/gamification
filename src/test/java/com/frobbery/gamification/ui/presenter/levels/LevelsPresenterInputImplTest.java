package com.frobbery.gamification.ui.presenter.levels;

import com.frobbery.gamification.dao.entity.User;
import com.frobbery.gamification.service.user.authentication.UserDetailsImpl;
import com.frobbery.gamification.ui.interactor.UIInteractor;
import com.frobbery.gamification.util.dto.AchievementDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LevelsPresenterInputImplTest {

    @Mock
    private UIInteractor interactor;

    @Mock
    private LevelsPresenterOutput presenterOutput;

    @InjectMocks
    private LevelsPresenterInputImpl sut;

    @BeforeEach
    void setPresenterOutput() {
        sut.setPresenterOutput(presenterOutput);
    }

    @Test
    void shouldGetAvailableLevelsNum() {
        //given
        var expectedLevelsNum = 4L;
        when(interactor.getAvailableLevelsNum()).thenReturn(expectedLevelsNum);

        //when
        var actualLevelsNum = sut.getAvailableLevelsNum();

        //then
        assertEquals(expectedLevelsNum, actualLevelsNum);
    }

    @Test
    void shouldGetLastOpenLevelNum() {
        //given
        var authentication = mock(Authentication.class);
        var userEmail = "userEmail";
        var expectedLevelNum = 1;
        when(authentication.getPrincipal()).thenReturn(new UserDetailsImpl(User.builder()
                .email(userEmail)
                .build()));
        when(interactor.getLastOpenLevelNum(userEmail)).thenReturn(expectedLevelNum);

        //when
        var actualLevelNum = sut.getLastOpenLevelNum(authentication);

        //then
        assertEquals(expectedLevelNum, actualLevelNum);
    }

    @Test
    void shouldGoToLevel() {
        //given
        var levelNum = 1;

        //when
        sut.goToLevel(levelNum);

        //then
        verify(presenterOutput, times(1)).goToLevel(levelNum);
    }

    @Test
    void shouldCheckForTimedAchievements() {
        //given
        var authentication = mock(Authentication.class);
        var userEmail = "userEmail";
        var achievement = mock(AchievementDto.class);
        when(authentication.getPrincipal()).thenReturn(new UserDetailsImpl(User.builder()
                .email(userEmail)
                .build()));
        when(interactor.getNewTimeAchievementsOfUser(userEmail)).thenReturn(List.of(achievement));

        //when
        sut.checkForTimedAchievements(authentication);

        //then
        verify(presenterOutput, times(1)).showAchievementDialog(achievement);
    }
}