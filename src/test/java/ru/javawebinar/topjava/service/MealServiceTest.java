package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private static final Logger log = getLogger(MealServiceTest.class);
    private static Map<String, Long> totalWorkTime = new HashMap<>();

    static {
        SLF4JBridgeHandler.install();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TestRule timeRule = new TestRule() {
        @Override
        public Statement apply(Statement base, Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    Date startTest = new Date();
                    base.evaluate();
                    Date endTest = new Date();
                    long workTime = endTest.getTime() - startTest.getTime();
                    totalWorkTime.put(description.getMethodName(), workTime);
                    log.info("Work time " + description.getMethodName() + ": " + workTime + "ms");
                }
            };
        }
    };

    @AfterClass
    public static void after() {
        log.info("Work time methods:");
        long totalTime = 0;
        for (String description :
                totalWorkTime.keySet()) {
            log.info("Work time " + description + ": " + totalWorkTime.get(description) + "ms");
            totalTime += totalWorkTime.get(description);
        }
        log.info("Work time methods: " + totalTime + "ms");
    }

    @Autowired
    private MealService service;

    @Test
    public void testDelete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    //@Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage(MEAL1_ID + "");
        service.delete(MEAL1_ID, 1);
    }

    @Test
    public void testSave() throws Exception {
        Meal created = getCreated();
        service.create(created, USER_ID);
        assertMatch(service.getAll(USER_ID), created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void testGet() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
    }

    @Test
    //@Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage(MEAL1_ID + "");
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    //@Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage(MEAL1.getId().toString());
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void testGetAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void testGetBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }
}