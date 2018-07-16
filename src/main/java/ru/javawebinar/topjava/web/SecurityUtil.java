package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private static volatile int userId = 1;

    public static int authUserId() {
        return userId;
    }

    public static synchronized void setUserId(int id) {
        userId = id;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}