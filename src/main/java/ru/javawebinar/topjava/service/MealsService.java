package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

public interface MealsService {
    List<Meal> getMealList();
    Meal getMeal(int id);
    void addMeal(Meal meal);
    void deleteMeal(int id);
    void updateMeal(int id, Meal meal);
}
