package com.frobbery.gamification.ui.presenter.levels.single;

import com.frobbery.gamification.dao.entity.User;
import com.frobbery.gamification.service.user.authentication.UserDetailsImpl;
import com.frobbery.gamification.ui.interactor.UIInteractor;
import com.frobbery.gamification.util.dto.AchievementDto;
import com.frobbery.gamification.util.dto.CheckCodeDto;
import com.frobbery.gamification.util.dto.LevelDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SinglePresenterInputImplTest {

    @Mock
    private UIInteractor interactor;

    @Mock
    private SingleLevelPresenterOutput presenterOutput;

    @InjectMocks
    private SinglePresenterInputImpl sut;

    @BeforeEach
    void setPresenterOutput() {
        sut.setPresenterOutput(presenterOutput);
    }

    @Test
    void shouldGetCurrentLevel() {
        //given
        var levelNum = 0;
        var expectedLevel = mock(LevelDto.class);
        when(interactor.getLevelByNumber(levelNum)).thenReturn(expectedLevel);

        //when
        var actualLevel = sut.getCurrentLevel(levelNum);

        //then
        assertEquals(expectedLevel, actualLevel);
    }

    @Test
    void shouldShowSuccess_whenCheckTrueAndAuthenticated() {
        //given
        var authentication = mock(Authentication.class);
        var levelNumber = 0;
        var initialCode = "initialCode";
        var code = "code";
        var userEmail = "userEmail";
        var achievement = mock(AchievementDto.class);
        var checkCodeDto = CheckCodeDto.builder()
                .levelNumber(levelNumber)
                .initialCode(initialCode)
                .code(code)
                .build();
        when(interactor.checkCode(checkCodeDto)).thenReturn(true);
        when(interactor.isLastLevel(levelNumber)).thenReturn(false);
        when(authentication.getPrincipal()).thenReturn(new UserDetailsImpl(User.builder()
                .email(userEmail)
                .build()));
        when(interactor.addLevelAchievementToUser(userEmail, levelNumber, false)).thenReturn(achievement);

        //when
        sut.checkCode(authentication, levelNumber, initialCode, code);

        //then
        verify(presenterOutput, times(1)).showSuccessDialog(false);
        verify(presenterOutput, times(1)).showAchievementDialog(achievement);
    }

    @Test
    void shouldShowSuccess_whenCheckTrueAndAuthenticationNull() {
        //given
        var levelNumber = 0;
        var initialCode = "initialCode";
        var code = "code";
        var checkCodeDto = CheckCodeDto.builder()
                .levelNumber(levelNumber)
                .initialCode(initialCode)
                .code(code)
                .build();
        when(interactor.checkCode(checkCodeDto)).thenReturn(true);
        when(interactor.isLastLevel(levelNumber)).thenReturn(true);

        //when
        sut.checkCode(null, levelNumber, initialCode, code);

        //then
        verify(presenterOutput, times(1)).showSuccessDialog(true);
    }

    @Test
    void shouldShowSuccess_whenCheckTrueAndAuthenticationAnonymous() {
        //given
        var authentication = mock(AnonymousAuthenticationToken.class);
        var levelNumber = 0;
        var initialCode = "initialCode";
        var code = "code";
        var checkCodeDto = CheckCodeDto.builder()
                .levelNumber(levelNumber)
                .initialCode(initialCode)
                .code(code)
                .build();
        when(interactor.checkCode(checkCodeDto)).thenReturn(true);
        when(interactor.isLastLevel(levelNumber)).thenReturn(true);

        //when
        sut.checkCode(authentication, levelNumber, initialCode, code);

        //then
        verify(presenterOutput, times(1)).showSuccessDialog(true);
    }

    @Test
    void shouldShowFailure_whenCheckFalse() {
        //given
        var levelNumber = 0;
        var initialCode = "initialCode";
        var code = "code";
        var checkCodeDto = CheckCodeDto.builder()
                .levelNumber(levelNumber)
                .initialCode(initialCode)
                .code(code)
                .build();
        when(interactor.checkCode(checkCodeDto)).thenReturn(false);

        //when
        sut.checkCode(null, levelNumber, initialCode, code);

        //then
        verify(presenterOutput, times(1)).showFailureDialog();
    }
}