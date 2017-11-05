package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealsServiceMemory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealAddServlet extends HttpServlet {
    private static final Logger log = getLogger(MealAddServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("meal add servlet");
        String message = "";

        if("add".equals(req.getParameter("action"))) {
            try {
                LocalDateTime date = LocalDateTime.parse(req.getParameter("date"));
                String description = req.getParameter("description");
                int calories = Integer.parseInt(req.getParameter("calories"));
                new MealsServiceMemory().addMeal(new Meal(-1, date, description, calories));
            }catch (NumberFormatException | DateTimeParseException e) {
                message = "?message=wrong format";
            } catch (SecurityException e) {
                message = "?message=id do not exist";
            }
            resp.sendRedirect("meals" + message);
        } else {
            req.getRequestDispatcher("/mealAdd.jsp").forward(req, resp);
        }
    }
}
