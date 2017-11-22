package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(100008, ADMIN_ID);
        assertEquals(meal, MEAL_ONE);
    }

    @Test
    public void delete() throws Exception {
        Meal meal = service.create(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0),
                "asd", 1000), ADMIN_ID);
        service.delete(meal.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void notUserDelete() throws Exception {
        service.delete(100002, ADMIN_ID);
    }

    /*@Test
    public void getBetweenDates() throws Exception {
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
    }*/

    @Test
    public void getAll() throws Exception {
        List<Meal> meals = service.getAll(ADMIN_ID);
        assertEquals(meals, Arrays.asList(MEAL_TWO, MEAL_ONE));
    }

    @Test
    public void update() throws Exception {
        service.update(MEAL_UPDATE, ADMIN_ID);
        service.update(MEAL_TWO, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotUser() throws Exception {
        service.update(MEAL_UPDATE, USER_ID);
    }

    @Test
    public void create() throws Exception {
        Meal meal = service.create(MEAL_THRE, ADMIN_ID);
        service.delete(meal.getId(), ADMIN_ID);
    }

}