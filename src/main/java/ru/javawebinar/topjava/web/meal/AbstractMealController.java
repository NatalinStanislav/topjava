package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.service.MealService;

public abstract class AbstractMealController {
    @Autowired
    protected MealService service;

    protected static Logger log;
}
