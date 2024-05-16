package com.frobbery.gamification.check.service.level_test;

import com.frobbery.gamification.dao.LevelTestRepository;
import com.frobbery.gamification.dao.entity.LevelTest;
import com.frobbery.gamification.util.dto.LevelTestDto;
import com.frobbery.gamification.util.mapper.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LevelTestServiceImplTest {

    @Mock
    private LevelTestRepository levelTestRepository;

    @Mock
    private Mapper<LevelTest, LevelTestDto> mapper;

    private LevelTestServiceImpl sut;

    @BeforeEach
    void initSut() {
        sut = spy(new LevelTestServiceImpl(levelTestRepository, mapper));
    }

    @Test
    void shouldTestLevelCodeSuccess() {
        //given
        var levelNumber = 1;
        var insertCode = "Кукареку!";
        var levelTest = mock(LevelTest.class);
        var levelTestDto = LevelTestDto.builder()
                .className("com.frobbery.gamification.check.compile.FirstLevelTest")
                .code("""
                        package com.frobbery.gamification.check.compile;

                        public class FirstLevelTest implements LevelTestClass {

                            private final static String wakeUpSignal = "Кукареку!";

                            @Override
                            public Boolean getTestResult() {
                                String actualSignal = "__INSERT__";
                                return actualSignal.equals(wakeUpSignal);
                            }
                        }""")
                .build();
        when(levelTestRepository.findByLevelNumber(levelNumber)).thenReturn(Optional.of(levelTest));
        when(mapper.map(levelTest)).thenReturn(levelTestDto);

        //when
        var result = sut.testLevelCode(levelNumber, insertCode);

        //then
        assertTrue(result);
    }

    @Test
    void shouldTestLevelCodeFail_whenCompileFail() {
        //given
        var levelNumber = 1;
        var insertCode = "Кукареку!";
        var levelTest = mock(LevelTest.class);
        var levelTestDto = LevelTestDto.builder()
                .className("com.frobbery.gamification.check.compile.FirstLevelTest")
                .code("""
                        package com.frobbery.gamification.check.compile;

                        public class FirstLevelTest implements LevelTestClass {{

                            private final static String wakeUpSignal = "Кукареку!";

                            @Override
                            public Boolean getTestResult() {
                                String actualSignal = "__INSERT__";
                                return actualSignal.equals(wakeUpSignal);
                            }
                        }""")
                .build();
        when(levelTestRepository.findByLevelNumber(levelNumber)).thenReturn(Optional.of(levelTest));
        when(mapper.map(levelTest)).thenReturn(levelTestDto);

        //when
        var result = sut.testLevelCode(levelNumber, insertCode);

        //then
        assertFalse(result);
    }

    @Test
    void shouldTestLevelCodeFail_whenExceptionThrown() {
        //given
        var levelNumber = 1;
        var insertCode = "Кукареку!";
        var levelTest = mock(LevelTest.class);
        var levelTestDto = LevelTestDto.builder()
                .className("com.frobbery.gamification.check.compile.FirstLevelTest")
                .code("""
                        package com.frobbery.gamification.check.compile;

                        public abstract class FirstLevelTest implements LevelTestClass {

                            private final static String wakeUpSignal = "Кукареку!";

                            @Override
                            public Boolean getTestResult() {
                                String actualSignal = "__INSERT__";
                                return actualSignal.equals(wakeUpSignal);
                            }
                        }""")
                .build();
        when(levelTestRepository.findByLevelNumber(levelNumber)).thenReturn(Optional.of(levelTest));
        when(mapper.map(levelTest)).thenReturn(levelTestDto);

        //when
        var result = sut.testLevelCode(levelNumber, insertCode);

        //then
        assertFalse(result);
    }

    @Test
    void shouldTestLevelCodeFail_whenNotInstanceOfLevelTest() {
        //given
        var levelNumber = 1;
        var insertCode = "Кукареку!";
        var levelTest = mock(LevelTest.class);
        var levelTestDto = LevelTestDto.builder()
                .className("com.frobbery.gamification.check.compile.FirstLevelTest")
                .code("""
                        package com.frobbery.gamification.check.compile;

                        public class FirstLevelTest {
                        }""")
                .build();
        when(levelTestRepository.findByLevelNumber(levelNumber)).thenReturn(Optional.of(levelTest));
        when(mapper.map(levelTest)).thenReturn(levelTestDto);
        when(sut.generateRandomClassName(levelTestDto)).thenReturn(levelTestDto);

        //when
        var result = sut.testLevelCode(levelNumber, insertCode);

        //then
        assertFalse(result);
    }
}