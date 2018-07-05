package ru.javawebinar.topjava.services;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryMealService implements MealService {
    private Map<Long, Meal> meals = Collections.synchronizedMap(new HashMap<>());
    private final AtomicLong increment = new AtomicLong();

    {
        for (Meal meal : MealsUtil.testData()) {
            long id = increment.incrementAndGet();
            meal.setId(id);
            meals.put(id, meal);
        }
    }

    @Override
    public Meal findOne(Long id) {
        return meals.get(id);
    }

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void delete(Long id) {
        meals.remove(id);
    }

    @Override
    public void save(Meal meal) {
        if (meal.getId() == null)
            meal.setId(increment.incrementAndGet());
        meals.put(meal.getId(), meal);
    }
}
