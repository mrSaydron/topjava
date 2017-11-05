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

public class MealEditServlet extends HttpServlet {
    private static final Logger log = getLogger(MealEditServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("meal edit servlet");
        String message = "";

        if("edit".equals(req.getParameter("action"))){
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                LocalDateTime date = LocalDateTime.parse(req.getParameter("date"));
                String description = req.getParameter("description");
                int calories = Integer.parseInt(req.getParameter("calories"));
                new MealsServiceMemory().updateMeal(id, new Meal(id, date, description, calories));
            }catch (NumberFormatException | DateTimeParseException e) {
                message = "?message=wrong format";
            } catch (SecurityException e) {
                message = "?message=id do not exist";
            }

            resp.sendRedirect("meals" + message);
        } else {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("meal", new MealsServiceMemory().getMeal(id));
                req.getRequestDispatcher("/mealEdit.jsp").forward(req, resp);
            }catch (NumberFormatException e) {
                resp.sendRedirect("meals?message=wrong format");
            }
        }
    }
}
