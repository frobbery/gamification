package com.frobbery.gamification.service.level;

import com.frobbery.gamification.dao.level.LevelRepository;
import com.frobbery.gamification.dao.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;

    private final UserRepository userRepository;

    @Override
    public int getAllAvailableNum() {
        return levelRepository.count();
    }

    @Override
    public int getLastOpenNum(String userEmail) {
        return userRepository.getLastAvailableLevelByEmail(userEmail);
    }
}
