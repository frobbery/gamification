package com.frobbery.gamification.check.service;

import com.frobbery.gamification.check.service.level_test.LevelTestService;
import com.frobbery.gamification.util.dto.CheckCodeDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckCodeServiceImplTest {

    @Mock
    private LevelTestService levelTestService;

    @InjectMocks
    private CheckCodeServiceImpl sut;

    @Test
    void shouldCheckCode_whenStartsAndEndsWithInitialCode() {
        //given
        var levelNumber = 1;
        var checkCodeDto = CheckCodeDto.builder()
                .levelNumber(levelNumber)
                .initialCode("code before ... code after")
                .code("code before code between code after")
                .build();
        when(levelTestService.testLevelCode(levelNumber, "code between")).thenReturn(true);

        //when
        var result = sut.check(checkCodeDto);

        //then
        assertTrue(result);
    }

    @Test
    void shouldCheckCodeFail_whenNotStartsWithInitialCode() {
        //given
        var levelNumber = 1;
        var checkCodeDto = CheckCodeDto.builder()
                .levelNumber(levelNumber)
                .initialCode("code before ... code after")
                .code("before code between code after")
                .build();

        //when
        var result = sut.check(checkCodeDto);

        //then
        assertFalse(result);
    }

    @Test
    void shouldCheckCodeFail_whenNotEndsWithInitialCode() {
        //given
        var levelNumber = 1;
        var checkCodeDto = CheckCodeDto.builder()
                .levelNumber(levelNumber)
                .initialCode("code before ... code after")
                .code("code before code between code")
                .build();

        //when
        var result = sut.check(checkCodeDto);

        //then
        assertFalse(result);
    }
}