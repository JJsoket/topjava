package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        service.save(meal, SecurityUtil.authUserId());
        response.sendRedirect("meals");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                service.delete(id, SecurityUtil.authUserId());
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        service.get(getId(request), SecurityUtil.authUserId());
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "login":
                int userId = Integer.parseInt(request.getParameter("login"));
                log.info("Set login user, now it is " + userId);
                SecurityUtil.setUserId(userId);
            case "all":
            default:
                log.info("getAll");
                String temp = request.getParameter("dateFrom");
                final LocalDate dFrom =
                        temp != null && !temp.isEmpty() ? LocalDate.parse(temp) : null;
                temp = request.getParameter("dateTo");
                final LocalDate dTo =
                        temp != null && !temp.isEmpty() ? LocalDate.parse(temp) : null;
                temp = request.getParameter("timeFrom");
                final LocalTime tFrom =
                        temp != null && !temp.isEmpty() ? LocalTime.parse(temp) : LocalTime.MIN;
                temp = request.getParameter("timeTo");
                final LocalTime tTo =
                        temp != null && !temp.isEmpty() ? LocalTime.parse(temp) : LocalTime.MAX;
                request.setAttribute("meals",
                        MealsUtil.getFilteredWithExceeded(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay(), tFrom, tTo).
                                stream().
                                filter(M -> (dFrom == null || !dFrom.isAfter(M.getDateTime().toLocalDate())) && (dTo == null || !dTo.isBefore(M.getDateTime().toLocalDate()))).collect(Collectors.toList()));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

}