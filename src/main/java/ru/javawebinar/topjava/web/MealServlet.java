package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealsService;
import ru.javawebinar.topjava.service.MealsServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("meals list");
        MealsService mealsService = new MealsServiceImpl();

        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealsService.getMealList(),
                LocalTime.MIN, LocalTime.MAX, 2000));
        request.setAttribute("asd", "asd");
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
