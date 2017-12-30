package ru.javawebinar.topjava.web.meal;

import com.sun.deploy.util.JarUtil;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

public class MealRestControllerTest extends AbstractControllerTest{
    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    public void testGetAll() throws Exception {
        AuthorizedUser.setId(ADMIN_ID);
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN_MEAL1, ADMIN_MEAL2));
    }

    @Test
    public void testGet() throws Exception {
        AuthorizedUser.setId(ADMIN_ID);
        mockMvc.perform(get(REST_URL + ADMIN_MEAL1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN_MEAL1));
    }

    @Test
    public void testGetBetween() throws Exception {
        AuthorizedUser.setId(USER_ID);
        mockMvc.perform(get(REST_URL + String.format("/between?startDate=%s&startTime=%s&endDate=%s&endTime=%s",
                "2015-05-30",
                "09:00:00",
                "2015-05-30",
                "14:00:00")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1, MEAL2));
    }

    @Test
    public void testDelete() throws Exception {
        AuthorizedUser.setId(ADMIN_ID);
        mockMvc.perform(delete(REST_URL + ADMIN_MEAL2.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(mealService.getAll(ADMIN_ID), ADMIN_MEAL1);
    }

    @Test
    public void testCreateMeal() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "new meal", 100);

        AuthorizedUser.setId(ADMIN_ID);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andDo(print())
                .andExpect(status().isCreated());

        Meal returned = TestUtil.readFromJson(action, Meal.class);
        newMeal.setId(returned.getId());

        assertMatch(newMeal, returned);
        assertMatch(mealService.getAll(ADMIN_ID), newMeal, ADMIN_MEAL2, ADMIN_MEAL1);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updateMeal = new Meal(ADMIN_MEAL1);
        updateMeal.setDateTime(LocalDateTime.now());

        AuthorizedUser.setId(ADMIN_ID);
        mockMvc.perform(put(REST_URL + updateMeal.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateMeal)))
                .andDo(print())
                .andExpect(status().isOk());

        assertMatch(mealService.get(ADMIN_MEAL1.getId(), ADMIN_ID), updateMeal);
    }

}