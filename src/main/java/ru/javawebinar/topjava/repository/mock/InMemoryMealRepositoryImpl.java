package ru.javawebinar.topjava.repository.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        for (User user : userRepository.getAll()) {
            repository.put(user.getId(), new HashMap<>());
            for (Meal meal : MealsUtil.meals()) {
                save(meal, user.getId());
            }
        }
        System.out.println(repository);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> storage = getRepository(userId);
        if (storage == null) return null;
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            storage.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return storage.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> storage = getRepository(userId);
        if (storage == null) return false;
        return storage.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> storage = getRepository(userId);
        if (storage == null) return null;
        return storage.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> storage = getRepository(userId);
        if (storage == null) return Collections.emptyList();
        return storage.values();
    }

    private Map<Integer, Meal> getRepository(int userId) {
        if (repository.containsKey(userId))
            return repository.get(userId);
        if (userRepository.get(userId) != null)
            repository.put(userId, new HashMap<>());
        return repository.get(userId);
    }
}

