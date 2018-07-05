package ru.javawebinar.topjava.services;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {
    Meal findOne(Long id);
    List<Meal> findAll();
    void delete(Long id);
    void save(Meal meal);
}
