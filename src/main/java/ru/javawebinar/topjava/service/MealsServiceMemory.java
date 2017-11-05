package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsServiceMemory implements MealsService {
    private static final Logger log = getLogger(MealsServiceMemory.class);
    private static ConcurrentHashMap<Integer, Meal> meals;
    private static Integer count;
    private static final Object lock = new Object();

    static
    {
        meals = new ConcurrentHashMap<>();
        meals.put(0, new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        meals.put(1, new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        meals.put(2, new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        meals.put(3, new Meal(3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        meals.put(4, new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        meals.put(5, new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        count = 5;
    }

    @Override
    public List<Meal> getMealList() {
        synchronized (lock) {
            log.debug("get meal list");
            return new ArrayList<>(meals.values());
        }
    }

    @Override
    public Meal getMeal(int id) throws ServiceException {
        synchronized (lock) {
            log.debug("get meal: " + id);
            if(!meals.containsKey(id)) {
                log.warn("meal do not find: " + id);
                throw new ServiceException("meal do not find: " + id);
            }
            return meals.get(id);
        }
    }

    @Override
    public void addMeal(Meal meal) {
        synchronized (lock) {
            count++;
            log.debug("add meal: " + count);
            meals.put(count, new Meal(count, meal.getDateTime(), meal.getDescription(), meal.getCalories()));
        }
    }

    @Override
    public void deleteMeal(int id) throws ServiceException {
        synchronized (lock) {
            log.debug("delete meal: " + id);
            if(!meals.containsKey(id)) {
                log.warn("meal do not find: " + id);
                throw new ServiceException("meal do not find: " + id);
            }
            meals.remove(id);
        }
    }

    @Override
    public void updateMeal(int id, Meal meal) throws ServiceException{
        synchronized (lock) {
            log.debug("update meal: " + id);
            if(!meals.containsKey(id)) {
                log.warn("meal do not find: " + id);
                throw new ServiceException("meal do not find: " + id);
            }
            meals.put(id, new Meal(id, meal.getDateTime(), meal.getDescription(), meal.getCalories()));
        }
    }
}
