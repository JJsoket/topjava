package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        int userId = counter.incrementAndGet();
        repository.put(userId, new User(userId, "User", "user@mail.ru", "user", Role.ROLE_USER));
        userId = counter.incrementAndGet();
        repository.put(userId, new User(userId, "Admin", "admin@mail.ru", "admin", Role.ROLE_ADMIN));
    }

    @Override
    public boolean delete(int id) {
        if (repository.containsKey(id)) {
            log.info("delete {}", id);
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
        } else {
            repository.put(user.getId(), user);
        }
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> users = new ArrayList<>(repository.values());
        Collections.sort(users, Comparator.comparing(User::getName));
        return users;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        for (User user : repository.values()) {
            if (user.getEmail().equals(email))
                return user;
        }
        return null;
    }
}
