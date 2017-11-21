package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final Meal MEAL_ONE = new Meal(START_SEQ + 8,
            LocalDateTime.of(2015, Month.JUNE, 1, 14, 0),
            "Админ ланч",
            510);

    public static final Meal MEAL_TWO = new Meal(START_SEQ + 9,
            LocalDateTime.of(2015, Month.JUNE, 1, 21, 0),
            "Админ ужин",
            1500);
}
