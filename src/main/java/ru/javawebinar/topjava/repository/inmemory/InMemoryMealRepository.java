package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
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
        if (repository.get(userId) == null) {
            repository.put(userId, new ConcurrentHashMap<>());
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete meal with userId = {} and mealId = {}", userId, id);
        Map<Integer, Meal> map = repository.get(userId);
        return map != null && map.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get meal with userId = {} and mealId = {}", userId, id);
        Map<Integer, Meal> map = repository.get(userId);
        return map != null ? map.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll meals with userId = {}", userId);
        List<Meal> meals = new ArrayList<>();
        if (repository.get(userId) != null) {
            meals.addAll(repository.get(userId).values());
        }
        meals.sort(Comparator.comparing(Meal::getDateTime).reversed());
        return meals;
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAllFiltered mealsTo with userId = {} startDate{}, endDate{}", userId, startDate, endDate);
        return MealsUtil.filterByDays(getAll(userId), startDate, endDate);
    }
}

