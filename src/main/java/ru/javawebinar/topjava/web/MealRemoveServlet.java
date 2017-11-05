package ru.javawebinar.topjava.web;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import ru.javawebinar.topjava.service.MealsServiceMemory;
import ru.javawebinar.topjava.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealRemoveServlet extends HttpServlet {
    private static final Logger log = getLogger(MealRemoveServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("meal remove servlet");
        String message = "";
        try {
            new MealsServiceMemory().deleteMeal(Integer.parseInt(req.getParameter("id")));
        }catch (NumberFormatException e) {
            message = "?message=id do not number";
        }catch (ServiceException e) {
            message = "?message=id do not exist";
        }
        resp.sendRedirect("meals" + message);
    }
}
