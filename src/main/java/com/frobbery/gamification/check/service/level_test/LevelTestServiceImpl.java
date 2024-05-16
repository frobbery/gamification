package com.frobbery.gamification.check.service.level_test;

import com.frobbery.gamification.check.compile.JavaSourceFromString;
import com.frobbery.gamification.check.compile.LevelTestClass;
import com.frobbery.gamification.check.compile.LevelTestClassManager;
import com.frobbery.gamification.dao.LevelTestRepository;
import com.frobbery.gamification.dao.entity.LevelTest;
import com.frobbery.gamification.util.dto.LevelTestDto;
import com.frobbery.gamification.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LevelTestServiceImpl implements LevelTestService {

    private final LevelTestRepository levelTestRepository;

    private final Mapper<LevelTest, LevelTestDto> mapper;

    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    private final LevelTestClassManager manager = new LevelTestClassManager(compiler.getStandardFileManager(null, null, null));

    public static final String INSERT = "__INSERT__";

    @Override
    public boolean testLevelCode(int levelNumber, String code) {
        var levelTestDtoWithInsertedCode = getLevelTestDtoWithInsertedCode(levelNumber, code);
        generateRandomClassName(levelTestDtoWithInsertedCode);
        return runLevelTestClass(levelTestDtoWithInsertedCode);
    }

    private LevelTestDto getLevelTestDtoWithInsertedCode(int levelNumber, String code) {
        var levelTestDto = levelTestRepository.findByLevelNumber(levelNumber)
                .map(mapper::map)
                .orElseThrow();
        levelTestDto.setCode(levelTestDto.getCode().replace(INSERT, code));
        return levelTestDto;
    }

    protected LevelTestDto generateRandomClassName(LevelTestDto levelTestDto) {
        var generatedRandomSuffix = UUID.randomUUID().toString().replace("-", "_");
        var newClassName = levelTestDto.getClassName() + generatedRandomSuffix;
        var newCode = levelTestDto.getCode().replace("LevelTest implements", MessageFormat.format("LevelTest{0} implements", generatedRandomSuffix));
        return LevelTestDto.builder()
                .className(newClassName)
                .code(newCode)
                .build();
    }

    private boolean runLevelTestClass(LevelTestDto levelTestDto) {
        try {
            List<JavaFileObject> sourceFiles = Collections.singletonList(new JavaSourceFromString(levelTestDto.getClassName(), levelTestDto.getCode()));
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, sourceFiles);
            boolean result = task.call();
            if (!result) {
                return false;
            } else {
                ClassLoader classLoader = manager.getClassLoader(null);
                Class<?> clazz = classLoader.loadClass(levelTestDto.getClassName());
                Object classInstance = clazz.getDeclaredConstructor().newInstance();
                if (classInstance instanceof  LevelTestClass) {
                    return ((LevelTestClass) classInstance).getTestResult();
                }
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
