package com.frobbery.gamification.util.mapper;

public interface Mapper<E, T> {

    T map(E objectToMap);
}
