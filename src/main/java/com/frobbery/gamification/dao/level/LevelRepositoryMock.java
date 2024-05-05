package com.frobbery.gamification.dao.level;

import com.frobbery.gamification.dao.entity.Level;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LevelRepositoryMock implements LevelRepository {

    private List<Level> levels;

    @PostConstruct
    private void initLevels() {
        levels = new ArrayList<>();
        levels.add(Level.builder()
                .number(1)
                .description("Для того чтоб начать играть, нужно разбудить персонажа. Он встает с первым “Кукареку!”")
                .hint("Подсказка: введи “Кукареку” в кавычки - так осуществляется консольный вывод")
                .initialCode("System.out.println(\"\");")
                .successMessage("Ты прошел 1-ый уровень и научился осуществлять консольный вывод!")
                .build());
    }

    @Override
    public int count() {
        return 4;
    }

    @Override
    public Optional<Level> findByNumber(int number) {
        return levels.stream()
                .filter(level -> level.getNumber() == number)
                .findAny();
    }
}
