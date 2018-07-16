package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface MealService {
    List<Meal> getAll(int userId);

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId)throws NotFoundException;

    Meal save(Meal meal, int userId);
}