package ru.javawebinar.topjava;

import java.time.LocalDateTime;
import java.util.*;

public class MealTestData {
    public static final Map<Integer, Map<Integer, MealWithEquals>> meals = new HashMap<>();

    static {
        HashMap<Integer, MealWithEquals> userMeals = new HashMap<>();
        userMeals.put(1, new MealWithEquals(1, LocalDateTime.of(2015, 5, 30, 10, 0), "Завтрак", 500));
        userMeals.put(2, new MealWithEquals(2, LocalDateTime.of(2015, 5, 30, 13, 0), "Обед", 1000));
        userMeals.put(3, new MealWithEquals(3, LocalDateTime.of(2015, 5, 30, 20, 0), "Ужин", 500));
        userMeals.put(4, new MealWithEquals(4, LocalDateTime.of(2015, 5, 31, 10, 0), "Завтрак", 1000));
        userMeals.put(5, new MealWithEquals(5, LocalDateTime.of(2015, 5, 31, 13, 0), "Обед", 500));
        userMeals.put(6, new MealWithEquals(6, LocalDateTime.of(2015, 5, 31, 20, 0), "Ужин", 510));
        meals.put(0, userMeals);
        userMeals = new HashMap<>();
        userMeals.put(7, new MealWithEquals(7, LocalDateTime.of(2015, 5, 31, 10, 0), "Завтрак", 1200));
        userMeals.put(8, new MealWithEquals(8, LocalDateTime.of(2015, 5, 31, 13, 0), "Обед", 1000));
        meals.put(1, userMeals);
    }
}
