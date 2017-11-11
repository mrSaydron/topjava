package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);
    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public void save(HttpServletRequest request) {
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")),
                Integer.valueOf(request.getParameter("usr")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        service.save(meal);
    }

    public Meal update(HttpServletRequest request) {
        return  "create".equals(request.getParameter("action")) ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, AuthorizedUser.id()) :
                service.get(getId(request), Integer.valueOf(request.getParameter("usr")));
    }

    public void delete(HttpServletRequest request) {
        int id = getId(request);
        log.info("Delete {}", id);
        service.delete(id, Integer.valueOf(request.getParameter("usr")));
    }

    public Meal get(int id, int userId) {
        return service.get(id, userId);
    }

    public Collection<MealWithExceed> getAll(HttpServletRequest request) {
        LocalDate startDate = LocalDate.MIN;
        LocalDate endDate = LocalDate.MAX;
        LocalTime startTime = LocalTime.MIN;
        LocalTime endTime = LocalTime.MAX;

        try {
            String t = request.getParameter("start_date");
            startDate = LocalDate.parse(Objects.requireNonNull(request.getParameter("start_date")));
        } catch (DateTimeParseException | NullPointerException e) {}
        try {
            String t = request.getParameter("end_date");
            endDate = LocalDate.parse(Objects.requireNonNull(request.getParameter("end_date")));
        } catch (DateTimeParseException | NullPointerException e) {}
        try {
            String t = request.getParameter("start_time");
            startTime = LocalTime.parse(Objects.requireNonNull(request.getParameter("start_time")));
        } catch (DateTimeParseException | NullPointerException e) {}
        try {
            String t = request.getParameter("end_time");
            endTime = LocalTime.parse(Objects.requireNonNull(request.getParameter("end_time")));
        } catch (DateTimeParseException | NullPointerException e) {}

        return MealsUtil.getWithExceeded(service.getAll(
                Integer.valueOf(request.getParameter("usr"))),
                startDate,
                endDate,
                startTime,
                endTime,
                AuthorizedUser.getCaloriesPerDay());
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}