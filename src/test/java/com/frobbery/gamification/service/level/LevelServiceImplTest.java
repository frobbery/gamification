package com.frobbery.gamification.service.level;

import com.frobbery.gamification.dao.LevelRepository;
import com.frobbery.gamification.dao.UserRepository;
import com.frobbery.gamification.dao.entity.Achievement;
import com.frobbery.gamification.dao.entity.Level;
import com.frobbery.gamification.dao.entity.User;
import com.frobbery.gamification.util.dto.AchievementDto;
import com.frobbery.gamification.util.dto.LevelDto;
import com.frobbery.gamification.util.mapper.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LevelServiceImplTest {

    @Mock
    private LevelRepository levelRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper<Level, LevelDto> mapper;

    @Mock
    private Mapper<Achievement, AchievementDto> achievementMapper;

    private LevelServiceImpl sut;

    @BeforeEach
    void initSut() {
        sut = spy(new LevelServiceImpl(levelRepository, userRepository, mapper, achievementMapper));
    }

    @Test
    void shouldGetAllAvailableNum() {
        //given
        var expectedLevelNum = 1L;
        when(levelRepository.count()).thenReturn(expectedLevelNum);

        //when
        var actualLevelNum = sut.getAllAvailableNum();

        //then
        assertEquals(expectedLevelNum, actualLevelNum);
    }

    @Test
    void shouldGetLastOpenNum_whenUserHasLevels() {
        //given
        var userEmail = "userEmail";
        var expectedLastOpenNum = 1;
        when(userRepository.getLastOpenLevelNumberByEmail(userEmail)).thenReturn(expectedLastOpenNum);

        //when
        var actualLastOpenNum = sut.getLastOpenNum(userEmail);

        //then
        assertEquals(expectedLastOpenNum, actualLastOpenNum);
    }

    @Test
    void shouldGetFirstLevelNum_whenUserHasNoLevels() {
        //given
        var userEmail = "userEmail";
        when(userRepository.getLastOpenLevelNumberByEmail(userEmail)).thenReturn(0);
        doNothing().when(sut).addNewLevelToUser(userEmail, 1);

        //when
        var actualLevelNum = sut.getLastOpenNum(userEmail);

        //then
        assertEquals(1, actualLevelNum);
    }

    @Test
    void shouldGetByNumber() {
        //given
        var levelNumber = 1;
        var level = mock(Level.class);
        var expectedLevelDto = mock(LevelDto.class);
        when(levelRepository.findByNumber(levelNumber)).thenReturn(Optional.of(level));
        when(mapper.map(level)).thenReturn(expectedLevelDto);

        //when
        var actualLevelDto = sut.getByNumber(levelNumber);

        //then
        assertEquals(expectedLevelDto, actualLevelDto);
    }

    @Test
    void shouldReturnIfLevelIsLast() {
        //given
        var levelNumber = 1;
        when(levelRepository.count()).thenReturn(1L);

        //when
        var result = sut.isLast(levelNumber);

        //then
        assertTrue(result);
    }

    @Test
    void shouldReturnIfLevelIsNotLast() {
        //given
        var levelNumber = 1;
        when(levelRepository.count()).thenReturn(2L);

        //when
        var result = sut.isLast(levelNumber);

        //then
        assertFalse(result);
    }

    @Test
    void shouldGetLevelAchievement() {
        //given
        var levelNumber = 1;
        var achievement = mock(Achievement.class);
        var level = Level.builder()
                .achievement(achievement)
                .build();
        var expectedAchievementDto = mock(AchievementDto.class);
        when(levelRepository.findByNumber(levelNumber)).thenReturn(Optional.of(level));
        when(achievementMapper.map(achievement)).thenReturn(expectedAchievementDto);

        //when
        var actualAchievementDto = sut.getLevelAchievement(levelNumber);

        //then
        assertEquals(expectedAchievementDto, actualAchievementDto);
    }

    @Test
    void shouldAddNewLevelToUser_IfUserDoesNotHaveIt() {
        //given
        var userEmail = "userEmail";
        var levelNumber = 1;
        var level = mock(Level.class);
        var user = User.builder()
                .levels(new ArrayList<>())
                .build();
        when(levelRepository.findByNumber(levelNumber)).thenReturn(Optional.of(level));
        when(userRepository.findByEmailAndFetchLevelsEagerly(userEmail)).thenReturn(Optional.of(user));
        var userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userCaptor.capture())).thenReturn(user);

        //when
        sut.addNewLevelToUser(userEmail, levelNumber);

        //then
        assertThat(userCaptor.getValue().getLevels())
                .contains(level);
    }

    @Test
    void shouldNotAddNewLevelToUser_IfUserHasIt() {
        //given
        var userEmail = "userEmail";
        var levelNumber = 1;
        var level = mock(Level.class);
        var user = User.builder()
                .levels(List.of(level))
                .build();
        when(levelRepository.findByNumber(levelNumber)).thenReturn(Optional.of(level));
        when(userRepository.findByEmailAndFetchLevelsEagerly(userEmail)).thenReturn(Optional.of(user));

        //when
        sut.addNewLevelToUser(userEmail, levelNumber);

        //then
        verify(userRepository, times(0)).save(any());
    }
}