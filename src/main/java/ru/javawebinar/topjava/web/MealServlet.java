package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.services.MealService;
import ru.javawebinar.topjava.services.MemoryMealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final MealService mealService = new MemoryMealService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("d") != null)
            mealService.delete(Long.valueOf(req.getParameter("d")));
        if (req.getParameter("u") != null) {
            Meal meal = mealService.findOne(Long.valueOf(req.getParameter("u")));
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("/meal_form.jsp").forward(req, resp);
        } else {
            log.debug("forward to meals");
            List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(mealService.findAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            req.setAttribute("meals", meals);
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forward to meals method = post");
        req.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(LocalDateTime.parse(req.getParameter("time")), req.getParameter("description"), Integer.valueOf(req.getParameter("calories")));
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty())
            meal.setId(Long.valueOf(id));
        mealService.save(meal);
        doGet(req, resp);
    }
}
