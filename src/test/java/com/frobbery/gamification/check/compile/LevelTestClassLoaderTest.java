package com.frobbery.gamification.check.compile;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SuppressWarnings("unused")
@ExtendWith(MockitoExtension.class)
class LevelTestClassLoaderTest {

    @Mock
    private ClassLoader parent;

    @Mock
    private LevelTestClassManager manager;

    @InjectMocks
    private LevelTestClassLoader sut;

    @Test
    @SneakyThrows
    void shouldGThrowClassNotFound_whenClassNotFound() {
        //given
        var name = "LevelTestClassAsBytes";
        when(manager.getBytesMap()).thenReturn(Map.of());

        //then
        assertThrows(ClassNotFoundException.class, () -> sut.findClass(name));
    }

    @Test
    @SneakyThrows
    void shouldReturnClass_whenClassFound() {
        //given
        var className = "com.frobbery.gamification.check.compile.FirstLevelTest";
        List<JavaFileObject> sourceFiles = Collections.singletonList(new JavaSourceFromString(className,
                """
                        package com.frobbery.gamification.check.compile;

                        public class FirstLevelTest implements LevelTestClass {

                            private final static String wakeUpSignal = "Кукареку!";

                            @Override
                            public Boolean getTestResult() {
                                String actualSignal = "__INSERT__";
                                return actualSignal.equals(wakeUpSignal);
                            }
                        }"""));
        var compiler = ToolProvider.getSystemJavaCompiler();
        var manager = new LevelTestClassManager(compiler.getStandardFileManager(null, null, null));
        var task = compiler.getTask(null, manager, null, null, null, sourceFiles);
        task.call();
        var sut = (LevelTestClassLoader) manager.getClassLoader(null);

        //when
        var result = sut.findClass(className);

        //then
        assertEquals(className, result.getName());
    }
}