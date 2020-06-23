package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(1, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save meal{} with userId = {}", meal, userId);
        repository.computeIfAbsent(userId, k -> new HashMap<>());
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
        return getFilteredList(userId, LocalDate.MIN.plusDays(1), LocalDate.MAX.minusDays(1));
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAllFiltered mealsTo with userId = {} startDate{}, endDate{}", userId, startDate, endDate);
        return getFilteredList(userId, startDate, endDate);
    }

    private List<Meal> getFilteredList(int userId, LocalDate startDate, LocalDate endDate) {
        List<Meal> meals = new ArrayList<>();
        if (repository.get(userId) != null) {
            meals = repository.get(userId).values().stream()
                    .filter(meal -> meal.getDate().isAfter(startDate.minusDays(1)) && meal.getDate().isBefore(endDate.plusDays(1)))
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        }
        return meals;
    }
}

