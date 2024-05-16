package com.frobbery.gamification.ui.interactor;

import com.frobbery.gamification.check.service.CheckCodeService;
import com.frobbery.gamification.service.achievement.AchievementService;
import com.frobbery.gamification.service.level.LevelService;
import com.frobbery.gamification.service.user.UserService;
import com.frobbery.gamification.util.dto.AchievementDto;
import com.frobbery.gamification.util.dto.AuthorizeDto;
import com.frobbery.gamification.util.dto.CheckCodeDto;
import com.frobbery.gamification.util.dto.LevelDto;
import com.frobbery.gamification.util.dto.ReceivedAchievementDto;
import com.frobbery.gamification.util.dto.RegistryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UIInteractorImplTest {

    @Mock
    private UserService userService;

    @Mock
    private LevelService levelService;

    @Mock
    private AchievementService achievementService;

    @Mock
    private CheckCodeService checkCodeService;

    @InjectMocks
    private UIInteractorImpl sut;

    @Test
    void shouldCheckIfUserExists() {
        //given
        var registryDto = RegistryDto.builder()
                .email("email")
                .nickName("nickName")
                .password("password")
                .build();
        when(userService.checkIfExists(registryDto.getNickName(), registryDto.getEmail())).thenReturn(false);

        //when
        var result = sut.checkIfUserExists(registryDto);

        //then
        assertFalse(result);
    }

    @Test
    void shouldRegisterUser() {
        //given
        var registryDto = mock(RegistryDto.class);

        //when
        sut.registerUser(registryDto);

        //then
        verify(userService, times(1)).register(registryDto);
    }

    @Test
    void shouldAuthorizeUser() {
        //given
        var authorizeDto = mock(AuthorizeDto.class);

        //when
        sut.authorizeUser(authorizeDto);

        //then
        verify(userService, times(1)).authorize(authorizeDto);
        verify(userService, times(1)).updateTimePeriod(authorizeDto.getEmail());
    }

    @Test
    void shouldReturnAvailableLevelsNum() {
        //given
        when(levelService.getAllAvailableNum()).thenReturn(1L);

        //when
        var result = sut.getAvailableLevelsNum();

        //then
        assertEquals(1L, result);
    }

    @Test
    void shouldGetLastOpenLevelNum() {
        //given
        var userEmail = "userEmail";
        when(levelService.getLastOpenNum(userEmail)).thenReturn(1);

        //when
        var result = sut.getLastOpenLevelNum(userEmail);

        //then
        assertEquals(1, result);
    }

    @Test
    void shouldGetLevelByNumber() {
        //given
        var levelNumber = 1;
        var expectedLevel = mock(LevelDto.class);
        when(levelService.getByNumber(1)).thenReturn(expectedLevel);

        //when
        var actualLevel = sut.getLevelByNumber(levelNumber);

        //then
        assertEquals(expectedLevel, actualLevel);
    }

    @Test
    void shouldGetNewTimeAchievementsOfUser() {
        //given
        var userEmail = "userEmail";
        var expectedAchievements = List.of(mock(AchievementDto.class));
        when(achievementService.getNewTimedAchievementsOfUser(userEmail)).thenReturn(expectedAchievements);

        //when
        var actualAchievements = sut.getNewTimeAchievementsOfUser(userEmail);

        //then
        assertThat(actualAchievements)
                .usingRecursiveComparison()
                .isEqualTo(expectedAchievements);
    }

    @Test
    void shouldGetReceivedAchievementOfUser() {
        //given
        var userEmail = "userEmail";
        var expectedReceivedAchievements = List.of(mock(ReceivedAchievementDto.class));
        when(achievementService.getReceivedAchievementOfUser(userEmail)).thenReturn(expectedReceivedAchievements);

        //when
        var actualReceivedAchievements = sut.getReceivedAchievementOfUser(userEmail);

        //then
        assertThat(actualReceivedAchievements)
                .usingRecursiveComparison()
                .isEqualTo(expectedReceivedAchievements);
    }

    @Test
    void shouldReturnIsLevelLast() {
        //given
        var levelNumber = 1;
        when(levelService.isLast(levelNumber)).thenReturn(true);

        //when
        var result = sut.isLastLevel(levelNumber);

        //then
        assertTrue(result);
    }

    @Test
    void shouldAddLevelAchievementToUser_whenLevelNotLast() {
        //given
        var userEmail = "userEmail";
        var levelNumber = 1;
        var expectedAchievement = mock(AchievementDto.class);
        when(levelService.getLevelAchievement(levelNumber)).thenReturn(expectedAchievement);
        when(achievementService.addAchievementToUser(userEmail, expectedAchievement.getName())).thenReturn(true);

        //when
        var actualAchievement = sut.addLevelAchievementToUser(userEmail, levelNumber, false);

        //then
        assertEquals(expectedAchievement, actualAchievement);
        verify(levelService, times(1)).addNewLevelToUser(userEmail, levelNumber + 1);
    }

    @Test
    void shouldNotAddLevelAchievementToUser_whenAchievementReceived() {
        //given
        var userEmail = "userEmail";
        var levelNumber = 1;
        var expectedAchievement = mock(AchievementDto.class);
        when(levelService.getLevelAchievement(levelNumber)).thenReturn(expectedAchievement);
        when(achievementService.addAchievementToUser(userEmail, expectedAchievement.getName())).thenReturn(false);

        //when
        var actualAchievement = sut.addLevelAchievementToUser(userEmail, levelNumber, false);

        //then
        assertThat(actualAchievement).isNull();
        verify(levelService, times(1)).addNewLevelToUser(userEmail, levelNumber + 1);
    }

    @Test
    void shouldAddLevelAchievementToUser_whenLevelLast() {
        //given
        var userEmail = "userEmail";
        var levelNumber = 1;
        var expectedAchievement = mock(AchievementDto.class);
        when(achievementService.addAchievementToUser(userEmail, expectedAchievement.getName())).thenReturn(true);
        when(levelService.getLevelAchievement(levelNumber)).thenReturn(expectedAchievement);

        //when
        var actualAchievement = sut.addLevelAchievementToUser(userEmail, levelNumber, true);

        //then
        assertEquals(expectedAchievement, actualAchievement);
        verify(levelService, times(0)).addNewLevelToUser(userEmail, levelNumber + 1);
    }

    @Test
    void shouldCheckCode() {
        //given
        var checkCodeDto = mock(CheckCodeDto.class);
        when(checkCodeService.check(checkCodeDto)).thenReturn(true);

        //when
        var result = sut.checkCode(checkCodeDto);

        //then
        assertTrue(result);
    }
}