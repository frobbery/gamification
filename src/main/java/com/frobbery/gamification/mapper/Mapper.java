package com.frobbery.gamification.mapper;

public interface Mapper<E, T> {

    T map(E objectToMap);
}
