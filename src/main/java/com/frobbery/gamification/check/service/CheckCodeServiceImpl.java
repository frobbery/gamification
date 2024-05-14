package com.frobbery.gamification.check.service;

import com.frobbery.gamification.check.service.level_test.LevelTestService;
import com.frobbery.gamification.util.dto.CheckCodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckCodeServiceImpl implements CheckCodeService{

    private final LevelTestService levelTestService;

    @Override
    public boolean check(CheckCodeDto checkCodeDto) {
        var initialCode = checkCodeDto.getInitialCode();
        var code = checkCodeDto.getCode();
        var dotsIndex = initialCode.indexOf("...");
        var initialCodeFirstPart = initialCode.substring(0, dotsIndex);
        var initialCodeSecondPart = initialCode.substring(dotsIndex + 3);
        if (code.startsWith(initialCodeFirstPart) && code.endsWith(initialCodeSecondPart)) {
            var insertedCode = code.substring(dotsIndex, code.indexOf(initialCodeSecondPart));
            return levelTestService.testLevelCode(checkCodeDto.getLevelNumber(), insertedCode);
        }
        return false;
    }
}
