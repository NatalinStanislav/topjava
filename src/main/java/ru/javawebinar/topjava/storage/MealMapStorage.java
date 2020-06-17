package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMapStorage implements Storage<Meal> {
    private Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    private AtomicInteger index = new AtomicInteger(0);


    @Override
    public Meal update(Meal meal) {
        return mealMap.merge(meal.getId(), meal, (m1, m2) -> m2);
    }

    @Override
    public Meal save(Meal meal) {
        meal.setId(index.incrementAndGet());
        return mealMap.merge(meal.getId(), meal, (m1, m2) -> m2);
    }

    @Override
    public Meal get(int id) {
        return mealMap.get(id);
    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }
}
