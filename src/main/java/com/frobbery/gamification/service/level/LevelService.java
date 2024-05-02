package com.frobbery.gamification.service.level;

public interface LevelService {

    int getAllAvailableNum();

    int getLastOpenNum(String userEmail);
}
