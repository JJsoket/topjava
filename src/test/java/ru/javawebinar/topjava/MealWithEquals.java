package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Objects;

public class MealWithEquals extends Meal {
    public MealWithEquals() {
    }

    public MealWithEquals(LocalDateTime dateTime, String description, int calories) {
        super(dateTime, description, calories);
    }

    public MealWithEquals(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id, dateTime, description, calories);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meal)) return false;
        Meal meal = (Meal) o;
        return getCalories() == meal.getCalories() &&
                Objects.equals(getDateTime(), meal.getDateTime()) &&
                Objects.equals(getDescription(), meal.getDescription());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getDateTime(), getDescription(), getCalories());
    }
}
