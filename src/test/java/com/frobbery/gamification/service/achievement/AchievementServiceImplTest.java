package com.frobbery.gamification.service.achievement;

import com.frobbery.gamification.dao.AchievementRepository;
import com.frobbery.gamification.dao.ReceivedAchievementRepository;
import com.frobbery.gamification.dao.UserRepository;
import com.frobbery.gamification.dao.entity.Achievement;
import com.frobbery.gamification.dao.entity.ReceivedAchievement;
import com.frobbery.gamification.dao.entity.User;
import com.frobbery.gamification.util.dto.AchievementDto;
import com.frobbery.gamification.util.dto.ReceivedAchievementDto;
import com.frobbery.gamification.util.mapper.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private ReceivedAchievementRepository receivedAchievementRepository;

    @Mock
    private Mapper<Achievement, AchievementDto> achievementMapper;

    @Mock
    private Mapper<ReceivedAchievement, ReceivedAchievementDto> receivedAchievementMapper;

    @InjectMocks
    private AchievementServiceImpl sut;

    @BeforeEach
    void initSut() {
        sut = new AchievementServiceImpl(userRepository, achievementRepository, receivedAchievementRepository, achievementMapper, receivedAchievementMapper);
    }
    @Test
    void shouldGetNewTimedAchievementsOfUser() {
        //given
        var userEmail = "userEmail";
        var user = User.builder()
                .id(1L)
                .currentEntryPeriod(1)
                .build();
        var achievement1 = Achievement.builder().id(1L).build();
        var achievement2 = Achievement.builder().id(2L).build();
        var receivedAchievementList = List.of(ReceivedAchievement.builder()
                .achievement(achievement1)
                .build());
        var receivedAchievement = ReceivedAchievement.builder()
                .achievement(achievement2)
                .user(user)
                .receiveDate(LocalDate.now())
                .build();
        var achievementDto = mock(AchievementDto.class);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(receivedAchievementRepository.findAllByUserId(eq(user.getId()))).thenReturn(receivedAchievementList);
        when(achievementRepository.findAllByEntryPeriod(user.getCurrentEntryPeriod())).thenReturn(List.of(achievement1, achievement2));
        when(receivedAchievementRepository.save(any())).thenReturn(receivedAchievement);
        when(achievementMapper.map(achievement2)).thenReturn(achievementDto);

        //when
        var actualAchievements = sut.getNewTimedAchievementsOfUser(userEmail);

        //then
        assertThat(actualAchievements)
                .usingRecursiveComparison()
                .isEqualTo(List.of(achievementDto));
    }

    @Test
    void shouldGetReceivedAchievementOfUser() {
        //given
        var userEmail = "userEmail";
        var user = User.builder()
                .id(1L)
                .currentEntryPeriod(1)
                .build();
        var receivedAchievement = mock(ReceivedAchievement.class);
        var receivedAchievementDto = mock(ReceivedAchievementDto.class);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(receivedAchievementRepository.findAllByUserId(user.getId())).thenReturn(List.of(receivedAchievement));
        when(receivedAchievementMapper.map(receivedAchievement)).thenReturn(receivedAchievementDto);

        //when
        var actualReceivedAchievementList = sut.getReceivedAchievementOfUser(userEmail);

        //then
        assertThat(actualReceivedAchievementList)
                .usingRecursiveComparison()
                .isEqualTo(List.of(receivedAchievementDto));
    }

    @Test
    void shouldNotAddAchievementToUser_whenAlreadyReceived() {
        //given
        var userEmail = "userEmail";
        var achievementName = "achievementName";
        var user = User.builder()
                .id(1L)
                .currentEntryPeriod(1)
                .build();
        var achievement = Achievement.builder().id(1L).build();
        var receivedAchievementList = List.of(ReceivedAchievement.builder()
                .achievement(achievement)
                .build());
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(receivedAchievementRepository.findAllByUserId(eq(user.getId()))).thenReturn(receivedAchievementList);
        when(achievementRepository.findByName(achievementName)).thenReturn(Optional.of(achievement));

        //when
        sut.addAchievementToUser(userEmail, achievementName);

        //then
        verify(receivedAchievementRepository, times(0)).save(any());
    }

    @Test
    void shouldAddAchievementToUser_whenNotReceived() {
        //given
        var userEmail = "userEmail";
        var achievementName = "achievementName";
        var user = User.builder()
                .id(1L)
                .currentEntryPeriod(1)
                .build();
        var achievement1 = Achievement.builder().id(1L).build();
        var achievement2 = Achievement.builder().id(2L).build();
        var receivedAchievementList = List.of(ReceivedAchievement.builder()
                .achievement(achievement1)
                .build());
        var expectedReceivedAchievement = ReceivedAchievement.builder()
                .achievement(achievement2)
                .user(user)
                .receiveDate(LocalDate.now())
                .build();
        var receivedAchievementCaptor = ArgumentCaptor.forClass(ReceivedAchievement.class);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(receivedAchievementRepository.findAllByUserId(eq(user.getId()))).thenReturn(receivedAchievementList);
        when(achievementRepository.findByName(achievementName)).thenReturn(Optional.of(achievement2));
        when(receivedAchievementRepository.save(receivedAchievementCaptor.capture())).thenReturn(expectedReceivedAchievement);

        //when
        sut.addAchievementToUser(userEmail, achievementName);

        //then
        assertThat(receivedAchievementCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(expectedReceivedAchievement);
    }
}