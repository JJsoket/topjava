package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.MealWithEquals;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    MealService mealService;


    @Test
    public void get() {
        Meal meal = mealService.get(1, UserTestData.USER_ID);
        Assert.assertEquals(MealTestData.meals.get(0).get(1), meal);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        mealService.get(1, UserTestData.ADMIN_ID);
    }

    @Test
    public void delete() {
        mealService.delete(7, UserTestData.ADMIN_ID);
        Assert.assertEquals(Arrays.asList(MealTestData.meals.get(1).get(8)), mealService.getAll(UserTestData.ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        mealService.delete(1, 1);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> actual = mealService.getBetweenDates(LocalDate.of(2015, 5, 30), LocalDate.of(2015, 5, 30), UserTestData.USER_ID);
        List<Meal> expected = Arrays.asList(MealTestData.meals.get(0).get(3), MealTestData.meals.get(0).get(2), MealTestData.meals.get(0).get(1));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> actual = mealService.getBetweenDateTimes(LocalDateTime.of(2015, 5, 31, 9, 0), LocalDateTime.of(2015, 5, 31, 11, 0), UserTestData.ADMIN_ID);
        List<Meal> expected = Arrays.asList(MealTestData.meals.get(1).get(7));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAll() {
        List<Meal> actual = mealService.getAll(UserTestData.USER_ID);
        ArrayList expected = new ArrayList(MealTestData.meals.get(0).values());
        expected.sort(Comparator.comparing(Meal::getDateTime).reversed());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void update() {
        int id = 1;
        mealService.update(new Meal(id, LocalDateTime.of(2007, 1, 1, 0, 0), "new", 0), UserTestData.USER_ID);
        List<Meal> actual = mealService.getAll(UserTestData.USER_ID);
        List<Meal> expected = new ArrayList<>(MealTestData.meals.get(0).values());
        Collections.sort(expected, Comparator.comparing(Meal::getDateTime).reversed());
        for (int i = 0; i < expected.size(); i++) {
            if (expected.get(i).getId() == id) {
                expected.set(i, new MealWithEquals(id, LocalDateTime.of(2007, 1, 1, 0, 0), "new", 0));
                break;
            }
        }
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() {
        mealService.update(new Meal(10, LocalDateTime.now(), "new", 0), UserTestData.USER_ID);
    }

    @Test
    public void create() {
        Meal meal = new Meal(LocalDateTime.now(), "new eat", 100);
        int id = mealService.create(meal, UserTestData.USER_ID).getId();
        meal = new MealWithEquals(meal.getDateTime(), meal.getDescription(), meal.getCalories());
        meal.setId(id);
        List<Meal> expected = new ArrayList<>(MealTestData.meals.get(0).values());
        List<Meal> actual = mealService.getAll(UserTestData.USER_ID);
        expected.add(meal);
        expected.sort(Comparator.comparing(Meal::getDateTime).reversed());
        Assert.assertEquals(expected, actual);
    }
}