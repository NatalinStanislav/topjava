package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        repository.put(1, new ConcurrentHashMap<>());
        MealsUtil.MEALS.forEach(meal -> save(1, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save meal{} with userId = {}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int mealId) {
        log.info("delete meal with userId = {} and mealId = {}", userId, mealId);
        Map<Integer, Meal> map = repository.get(userId);
        return map != null && map.remove(mealId) != null;
    }

    @Override
    public Meal get(int userId, int mealId) {
        log.info("get meal with userId = {} and mealId = {}", userId, mealId);
        Map<Integer, Meal> map = repository.get(userId);
        return map != null ? map.get(mealId) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll meals with userId = {}", userId);
        if (repository.get(userId) == null) {
            throw new NotFoundException("Вы не авторизованы!");
        }
        List<Meal> meals = new ArrayList<>(repository.get(userId).values());
        meals.sort(Comparator.comparing(Meal::getDateTime).reversed());
        return meals;
    }

    @Override
    public List<MealTo> getAllFiltered(int userId, int caloriesPerDay, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getAllFiltered mealsTo with userId = {} and calories = {} startDate{}, endDate{}, startTime{}, endTime{}", userId, caloriesPerDay, startDate, endDate, startTime, endTime);
        return MealsUtil.filterByPredicate(getAll(userId), caloriesPerDay,
                meal -> meal.getDate().isAfter(startDate.minusDays(1))
                        && meal.getDate().isBefore(endDate.plusDays(1))
                        && DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
    }
}

