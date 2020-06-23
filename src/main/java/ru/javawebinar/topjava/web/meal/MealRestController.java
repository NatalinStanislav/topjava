package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        log.info("getAll");
        return service.getAll(authUserId(), authUserCaloriesPerDay());
    }

    public List<MealTo> getAllFiltered(String startDate, String endDate, String startTime, String endTime) {
        if (startDate.trim().equals("")) {
            startDate = LocalDate.MIN.plusDays(1).toString();
        }
        if (endDate.trim().equals("")) {
            endDate = LocalDate.MAX.minusDays(1).toString();
        }
        if (startTime.trim().equals("")) {
            startTime = LocalTime.MIN.toString();
        }
        if (endTime.trim().equals("")) {
            endTime = LocalTime.MAX.toString();
        }
        log.info("getAllFiltered startDate{}, endDate{}, startTime{}, endTime{}", startDate, endDate, startTime, endTime);
        return service.getAllFiltered(authUserId(), authUserCaloriesPerDay(), LocalDate.parse(startDate),
                LocalDate.parse(endDate), LocalTime.parse(startTime), LocalTime.parse(endTime));
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(authUserId(), id);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(authUserId(), id);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(authUserId(), meal);
    }

    public void update(Meal meal, int mealId) {
        log.info("update {} with id={}", meal, mealId);
        assureIdConsistent(meal, mealId);
        service.update(authUserId(), meal);
    }
}